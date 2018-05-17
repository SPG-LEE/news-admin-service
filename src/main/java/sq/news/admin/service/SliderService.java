package sq.news.admin.service;

import java.util.List;

import sq.news.admin.entity.Slider;

public interface SliderService {
	List<Slider> findAll();

	Slider save(Slider slider);

	Slider update(Slider slider);

	List<Slider> update(List<Slider> sliders);

	void delete(long id);

	Slider findById(long id);

	List<Slider> findByParentId(long parentId);

	List<Slider> findParentIdIsNull();

	void merge(Slider slider);
}
