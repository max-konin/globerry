package com.globerry.project.service.service_classes;

import com.globerry.project.dao.IDao;
import com.globerry.project.domain.Month;
import com.globerry.project.domain.PropertyType;
import com.globerry.project.domain.Tag;
import com.globerry.project.domain.TagsType;
import com.globerry.project.service.gui.CheckBox;
import com.globerry.project.service.gui.ICheckBox;
import com.globerry.project.service.gui.IGuiComponent;
import com.globerry.project.service.gui.ISlider;
import com.globerry.project.service.gui.SelectBox;
import com.globerry.project.service.gui.Slider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import java.util.Calendar;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * Фабрика для создания контекстов приложения
 *
 * @author max
 */
@Component
@Scope(value = "singleton")
public class ApplicationContextFactory
{

	static public final int whoTagId = 1;
	static public final int whatTagId = 2;
	static public final int whenTagId = 3;
	static public final int startSliderId = 4;
	static public final int mapZoomId = 15;
	static public final int visaId = 13;
	static public final int rusLanguageId = 14;
	@Autowired
	IDao<Tag> tagDao;
	@Autowired
	IDao<PropertyType> propertyTypeDao;
	GloberryGuiContext prototype;

	/**
	 * Создает контекст приложения
	 *
	 * @return контекст
	 */
	public IApplicationContext createAppContext()
	{
		if (prototype == null)
		{
			createProtopype();
		}
		return new GloberryGuiContext(prototype);
	}

	/**
	 * Инициализирует прототип для содания контекстов приложения
	 */
	private void createProtopype()
	{
		prototype = new GloberryGuiContext();
		createAndAddComponentsMap();
		createAndAddTagsSelectBoxes();
		createAndAddMapZoom();
		createAndAddSliders();
		createAndAddVisaRusLanguage();
	}

	private void createAndAddComponentsMap()
	{
		HashMap<Integer, IGuiComponent> componentsMap = new HashMap<Integer, IGuiComponent>();
		prototype.setComponentsMap(componentsMap);
	}

	private void createAndAddTagsSelectBoxes() throws RuntimeException, IllegalArgumentException
	{
		List<Tag> tags = getTags();
		SelectBox whoTag = new SelectBox(whoTagId);
		SelectBox whatTag = new SelectBox(whatTagId);
		SelectBox whenTag = new SelectBox(whenTagId);
		fillTagsSelectBoxesWithTags(whenTag, whoTag, whatTag, tags);
		setTagsMapping(whoTag, whatTag, whenTag);
		addTagsToPrototype(whatTag, whoTag, whenTag);
	}

	private List<Tag> getTags() throws RuntimeException
	{
		List<Tag> tags;
		try
		{
			tags = tagDao.getAll(Tag.class);
		}
		catch (RuntimeException e)
		{
			prototype = null;
			throw new RuntimeException("tagDao.getAll(Tag.class) Failed, check DataBase state and data.", e);
		}
		return tags;
	}

	private void fillTagsSelectBoxesWithTags(SelectBox whenTag, SelectBox whoTag, SelectBox whatTag, List<Tag> tags)
	{
		for (Month month : Month.values())
		{
			whenTag.addValue(month.ordinal());
		}
		whenTag.setValue((Calendar.getInstance().get(Calendar.MONTH) + 1) % 12);//+1 для того чтобы доставались параметры для следующего месяца после текущего
		for (Tag tag : tags)
		{
			if (tag.getTagsType() == TagsType.WHO)
			{
				whoTag.addValue(tag.getId());
			}
			else
			{
				whatTag.addValue(tag.getId());
			}
		}
	}

	private void setTagsMapping(SelectBox whoTag, SelectBox whatTag, SelectBox whenTag) throws IllegalArgumentException
	{
		prototype.getComponentsMap().put(whoTagId, whoTag);
		GuiMap.componentAddHandler(whoTag);

		prototype.getComponentsMap().put(whatTagId, whatTag);
		GuiMap.componentAddHandler(whatTag);

		prototype.getComponentsMap().put(whenTagId, whenTag);
		GuiMap.componentAddHandler(whenTag);
	}

	private void addTagsToPrototype(SelectBox whatTag, SelectBox whoTag, SelectBox whenTag)
	{
		prototype.setWhatTag(whatTag);
		prototype.setWhoTag(whoTag);
		prototype.setWhenTag(whenTag);
	}

	private void createAndAddMapZoom() throws IllegalArgumentException
	{
		SelectBox mapZoom = new SelectBox(mapZoomId);
		fiillMapZoomWithValues(mapZoom);
		setMapZoomMapping(mapZoom);
		addMapZoomToPrototype(mapZoom);
	}

	private void fiillMapZoomWithValues(SelectBox mapZoom)
	{
		for (int i = 2; i < 9; i++)//значения зумов с 2 по 8
		{
			mapZoom.addValue(i);
		}
	}

	private void setMapZoomMapping(SelectBox mapZoom) throws IllegalArgumentException
	{
		prototype.getComponentsMap().put(mapZoomId, mapZoom);
		GuiMap.componentAddHandler(mapZoom);
	}

	private void addMapZoomToPrototype(SelectBox mapZoom)
	{
		prototype.setMapZoom(mapZoom);
	}

	private void createAndAddSliders() throws RuntimeException, IllegalArgumentException
	{
		List<PropertyType> properyTypes = getPropertyTypes();
		HashMap<String, ISlider> sliders = fillAndSetMappingForSliders(properyTypes);
		addSlidersToPrototype(sliders);
	}

	private List<PropertyType> getPropertyTypes() throws RuntimeException
	{
		List<PropertyType> properyTypes;
		try
		{
			properyTypes = propertyTypeDao.getAll(PropertyType.class);
		}
		catch (RuntimeException e)
		{
			prototype = null;
			throw e;
		}
		return properyTypes;
	}

	private void addSlidersToPrototype(HashMap<String, ISlider> sliders)
	{
		prototype.setSliders(sliders);
	}

	private HashMap<String, ISlider> fillAndSetMappingForSliders(List<PropertyType> properyTypes) throws IllegalArgumentException
	{
		int id = startSliderId;//Sliders starting ID
		HashMap<String, ISlider> sliders = new HashMap<String, ISlider>();
		for (PropertyType type : properyTypes)
		{
			Slider slider = new Slider(id, type);
			sliders.put(type.getName(), slider);
			prototype.getComponentsMap().put(id, slider);
			GuiMap.componentAddHandler(slider);
			id++;
			//TODO Затычка для dependingMounth.
			if (slider.getPropertyType().getId() >= 7)
			{
				slider.getPropertyType().setDependingMonth(true);
			}
		}
		return sliders;
	}

	private void createAndAddVisaRusLanguage() throws IllegalArgumentException
	{
		ICheckBox visa = new CheckBox(visaId);
		ICheckBox rusLanguage = new CheckBox(rusLanguageId);
		setVisaAndLanguageMapping(visa, rusLanguage);
		addVisaAndLanguageToPrototype(visa, rusLanguage);
	}

	private void setVisaAndLanguageMapping(ICheckBox visa, ICheckBox rusLanguage) throws IllegalArgumentException
	{
		prototype.getComponentsMap().put(visaId, visa);
		prototype.getComponentsMap().put(rusLanguageId, rusLanguage);

		GuiMap.componentAddHandler(visa);
		GuiMap.componentAddHandler(rusLanguage);
	}

	private void addVisaAndLanguageToPrototype(ICheckBox visa, ICheckBox rusLanguage)
	{
		prototype.setVisa(visa);
		prototype.setRusLanguage(rusLanguage);
	}
}
