package sq.news.admin.respository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import sq.news.admin.entity.Slider;

public interface SliderRepository extends JpaRepository<Slider, Long>, SliderCustomRepository {

	List<Slider> findByParentId(long parentId);

	List<Slider> findByParentIdIsNull(Sort sort);

}
