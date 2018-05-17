package sq.news.admin.service.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import sq.news.admin.entity.Slider;
import sq.news.admin.respository.SliderRepository;
import sq.news.admin.service.SliderService;

@Repository
public class SliderHibernateService implements SliderService {

    @Autowired
    private SliderRepository sliderRepository;

    @Override
    public List<Slider> findAll() {

        return sliderRepository.findAll();
    }

    @Override
    public Slider save(Slider slider) {
        sliderRepository.saveAndFlush(slider);
        return slider;
    }

    @Override
    public Slider update(Slider slider) {
        sliderRepository.saveAndFlush(slider);
        return slider;
    }

    @Override
    public List<Slider> update(List<Slider> sliders) {
        sliderRepository.saveAll(sliders);
        return sliders;
    }

    @Override
    public void delete(long id) {
        sliderRepository.deleteById(id);

    }

    @Override
    public Slider findById(long id) {
        if (sliderRepository.findById(id).isPresent()){
            return sliderRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public List<Slider> findByParentId(long parentId) {
        return sliderRepository.findByParentId(parentId);
    }

    @Override
    public List<Slider> findParentIdIsNull() {

        return sliderRepository
                .findByParentIdIsNull(new Sort(Direction.ASC, "sort"));
    }

    @Override
    public void merge(Slider slider) {
        sliderRepository.saveAndFlush(slider);

    }

}
