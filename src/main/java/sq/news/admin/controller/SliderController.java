package sq.news.admin.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sq.base.AppResult;
import sq.news.admin.constants.RestConstants;
import sq.news.admin.constants.SysConstants;
import sq.news.admin.entity.Role;
import sq.news.admin.entity.Admin;
import sq.news.admin.entity.Slider;
import sq.news.admin.service.SliderService;
import sq.util.AppResultBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "导航栏显示")
@RestController
@RequestMapping(path = "/admins")
public class SliderController {
	@Autowired
	private SliderService sliderService;

	@Autowired
	private AdminController adminController;

	@GetMapping("/{id}/menus")
	@ApiOperation(value = "获取某个管理员列表")
	public AppResult<List<Slider>> findOneAdminList(@RequestHeader("x-access-token") final String token,
			@PathVariable long id) {
		AppResult<Admin> adminResult = adminController.getAdmin(token);
		if (!adminResult.isSuccess()) {
			return AppResultBuilder.buildFailedMessageResult(RestConstants.NO_TOKEN);
		}
		Set<Role> roles = adminResult.getData().getRoles();

		if (roles.stream().filter(filter -> filter.getRoleName().equals(SysConstants.SUPERADMIN)).collect(Collectors.toList())
				.size() != 0) {
			List<Slider> adminSlider = sliderService.findParentIdIsNull();
			return AppResultBuilder.buildSuccessMessageResult(adminSlider, SysConstants.OPERATION_SUCCESS);
		}
		List<Slider> resuitSlider = new ArrayList<>();
		List<Slider> parentMenus = new ArrayList<>();
		Set<Slider> subMenus = new HashSet<>();
		Set<Slider> subsubMenus = new HashSet<>();
		for (Role role : roles) {
			for (Slider roleSlider : role.getMenus()) {
				if (roleSlider.getParent() == null) { // 一级目录
					if (parentMenus.stream().filter(filter -> filter.getId().equals(roleSlider.getId())).count() == 0) {
						System.out.println("============");
						roleSlider.setSubMenus(null);
						parentMenus.add(roleSlider);
					}
					continue;
				} else if (roleSlider.getParent().getParent() == null) {// 二级目录
					if (subMenus.stream().filter(filter -> filter.getId().equals(roleSlider.getId())).count() == 0) {
						roleSlider.setSubMenus(null);
						subMenus.add(roleSlider);
					}
					continue;
				} else if (roleSlider.getParent().getParent() != null && subsubMenus.stream()
						.filter(filter -> filter.getId().equals(roleSlider.getId())).count() == 0) {// 三级目录
					subsubMenus.add(roleSlider);
				}
			}
		}
		for (Slider subsubSlider : subsubMenus) {// 将二级目录补全
			if (subMenus.stream().filter(filter -> filter.getId().equals(subsubSlider.getParent().getId()))
					.count() == 0) {
				Slider createSubMenus = sliderService.findById(subsubSlider.getParent().getId());
				createSubMenus.setSubMenus(null);
				subMenus.add(createSubMenus);

			}

		}
		for (Slider subSlider : subMenus) {// 将一级目录补全
			if (parentMenus.stream().filter(filter -> filter.getId().equals(subSlider.getParent().getId()))
					.count() == 0) {
				Slider createParentMenus = sliderService.findById(subSlider.getParent().getId());
				createParentMenus.setSubMenus(null);
				parentMenus.add(createParentMenus);

			}

		}
		for (Slider subSlider : subMenus) {// 三级添加到二级
			subSlider.setSubMenus(null);
			Set<Slider> newMenus = new HashSet<>();
			for (Slider subsubSlider : subsubMenus) {

				if (subSlider.getId().equals(subsubSlider.getParent().getId())) {
					newMenus.add(subsubSlider);
				}
			}
			subSlider.setSubMenus(newMenus);

		}

		for (Slider parentSlider : parentMenus) {// 二级添加到一级
			parentSlider.setSubMenus(null);
			Set<Slider> newMenus = new HashSet<>();
			for (Slider subSlider : subMenus) {
				if (parentSlider.getId().equals(subSlider.getParent().getId())) {
					newMenus.add(subSlider);
				}
			}
			parentSlider.setSubMenus(newMenus);
			resuitSlider.add(parentSlider);
		}
		List<Slider> sortResultSlider = resuitSlider.stream().sorted(Comparator.comparing(Slider::getSort))
				.collect(Collectors.toList());
		return AppResultBuilder.buildSuccessMessageResult(sortResultSlider, SysConstants.OPERATION_SUCCESS);

	}

	@PutMapping("/menus/sort")
	@ApiOperation(value = "修改导航菜单排序")
	public AppResult<List<Slider>> sortMenus(@RequestHeader("x-access-token") final String token,
			@RequestBody final List<Slider> menus) {

		menus.stream().forEach(menu -> {
			Slider findMenu = sliderService.findById(menu.getId());
			findMenu.setSort(menu.getSort());
			sliderService.merge(findMenu);
		});
		return AppResultBuilder.buildSuccessMessageResult(menus, SysConstants.OPERATION_SUCCESS);
	}

	@GetMapping("/menus")
	@ApiOperation(value = "获取全部列表")
	public AppResult<List<Slider>> findAll(@RequestHeader("x-access-token") final String token) {
		AppResult<Admin> adminResult = adminController.getAdmin(token);
		if (!adminResult.isSuccess()) {
			return AppResultBuilder.buildFailedMessageResult(RestConstants.NO_TOKEN);
		}
		List<Slider> sliders = sliderService.findParentIdIsNull();

		return AppResultBuilder.buildSuccessMessageResult(sliders, SysConstants.OPERATION_SUCCESS);
	}

	@PostMapping("/menus")
	@ApiOperation(value = "添加列表")
	public AppResult<Slider> addSlider(@RequestHeader("x-access-token") final String token,
			@RequestBody Slider slider) {
		AppResult<Admin> adminResult = adminController.getAdmin(token);
		if (!adminResult.isSuccess()) {
			return AppResultBuilder.buildFailedMessageResult(RestConstants.NO_TOKEN);
		}
		sliderService.save(slider);
		return AppResultBuilder.buildSuccessMessageResult(SysConstants.OPERATION_SUCCESS);

	}

	@PutMapping("/menus/{id}")
	@ApiOperation(value = "更改列表")
	public AppResult<Slider> updateSlider(@RequestHeader("x-access-token") final String token,
			@PathVariable final long id, @RequestBody Slider slider) {
		AppResult<Admin> adminResult = adminController.getAdmin(token);
		if (!adminResult.isSuccess()) {

			return AppResultBuilder.buildFailedMessageResult(RestConstants.NO_TOKEN);
		}
		Slider resultSlider = sliderService.findById(id);
		if (slider.getName() != null) {
			resultSlider.setName(slider.getName());
		}

		if (slider.getIcon() != null) {
			resultSlider.setIcon(slider.getIcon());

		}
		if (slider.getRoles() != null) {
			resultSlider.setRoles(slider.getRoles());
		}
		if (slider.getPath() != null) {
			resultSlider.setPath(slider.getPath());
		}
		if (slider.getTitle() != null) {
			resultSlider.setTitle(slider.getTitle());
		}
		sliderService.save(resultSlider);
		return AppResultBuilder.buildSuccessMessageResult(SysConstants.OPERATION_SUCCESS);

	}

	@DeleteMapping("/menus/{id}")
	@ApiOperation(value = "删除列表")
	public AppResult<Slider> deleteSlider(@RequestHeader("x-access-token") final String token,
			@PathVariable final long id) {
		AppResult<Admin> adminResult = adminController.getAdmin(token);
		if (!adminResult.isSuccess()) {
			return AppResultBuilder.buildFailedMessageResult(RestConstants.NO_TOKEN);
		}
		Slider resultSlider = sliderService.findById(id);
		if (resultSlider.getSubMenus().size() != 0) {
			for (Slider subSlider : resultSlider.getSubMenus()) {
				if (subSlider.getSubMenus().size() != 0) {
					for (Slider subsubSlider : subSlider.getSubMenus()) {
						sliderService.delete(subsubSlider.getId());
					}
				}
				sliderService.delete(subSlider.getId());

			}
		}
		sliderService.delete(resultSlider.getId());
		return AppResultBuilder.buildSuccessMessageResult(SysConstants.OPERATION_SUCCESS);

	}

}
