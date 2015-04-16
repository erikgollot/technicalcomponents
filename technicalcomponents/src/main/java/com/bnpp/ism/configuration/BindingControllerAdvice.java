package com.bnpp.ism.configuration;

import java.beans.PropertyEditorSupport;
import java.io.IOException;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import com.bnpp.ism.api.exchangedata.application.ApplicationVersionView;
import com.bnpp.ism.api.exchangedata.application.BuiltOnView;
import com.bnpp.ism.api.exchangedata.application.CanRunOnView;
import com.bnpp.ism.api.exchangedata.component.ComponentCatalogView;
import com.bnpp.ism.api.exchangedata.component.ComponentCategoryView;
import com.bnpp.ism.api.exchangedata.component.ComponentVersionInfoView;
import com.bnpp.ism.api.exchangedata.component.ImageGallery;
import com.bnpp.ism.api.exchangedata.component.ObsolescenceStrategyView;
import com.bnpp.ism.api.exchangedata.component.TechnicalComponentView;
import com.bnpp.ism.api.exchangedata.storage.SimpleStoredFileVersionView;
import com.bnpp.ism.api.exchangedata.storage.StorageView;
import com.bnpp.ism.api.exchangedata.storage.StoredFileVersionView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice(basePackages = "com.bnpp.ism.mvc")
public class BindingControllerAdvice {

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// Storage
		binder.registerCustomEditor(StorageView.class,
				new PropertyEditorSupport() {
					Object value;
					ObjectMapper mapper = new ObjectMapper();

					@Override
					public Object getValue() {
						return value;
					}

					@Override
					public void setAsText(String text)
							throws IllegalArgumentException {
						try {
							value = mapper.readValue(text, StorageView.class);
						} catch (IOException e) {
							MappingException ex = new MappingException();
							ex.setMessage("Cannot convert string " + text
									+ " to object class "
									+ StorageView.class.getName());
							throw ex;
						}
					}

					@Override
					public String getAsText() {
						try {
							return mapper.writeValueAsString(value);
						} catch (JsonProcessingException e) {
							MappingException ex = new MappingException();
							ex.setMessage("Cannot convert object of type "
									+ StorageView.class.getName()
									+ " to string");
							throw ex;
						}
					}
				}

		);
		binder.registerCustomEditor(StoredFileVersionView.class,
				new PropertyEditorSupport() {
					Object value;
					ObjectMapper mapper = new ObjectMapper();

					@Override
					public Object getValue() {
						return value;
					}

					@Override
					public void setAsText(String text)
							throws IllegalArgumentException {
						try {
							value = mapper.readValue(text,
									StoredFileVersionView.class);
						} catch (IOException e) {
							MappingException ex = new MappingException();
							ex.setMessage("Cannot convert string " + text
									+ " to object class "
									+ StoredFileVersionView.class.getName());
							throw ex;
						}
					}

					@Override
					public String getAsText() {
						try {
							return mapper.writeValueAsString(value);
						} catch (JsonProcessingException e) {
							MappingException ex = new MappingException();
							ex.setMessage("Cannot convert object of type "
									+ StoredFileVersionView.class.getName()
									+ " to string");
							throw ex;
						}
					}
				});
		binder.registerCustomEditor(SimpleStoredFileVersionView.class,
				new PropertyEditorSupport() {
					Object value;
					ObjectMapper mapper = new ObjectMapper();

					@Override
					public Object getValue() {
						return value;
					}

					@Override
					public void setAsText(String text)
							throws IllegalArgumentException {
						try {
							value = mapper.readValue(text,
									SimpleStoredFileVersionView.class);
						} catch (IOException e) {
							MappingException ex = new MappingException();
							ex.setMessage("Cannot convert string "
									+ text
									+ " to object class "
									+ SimpleStoredFileVersionView.class
											.getName());
							throw ex;
						}
					}

					@Override
					public String getAsText() {
						try {
							return mapper.writeValueAsString(value);
						} catch (JsonProcessingException e) {
							MappingException ex = new MappingException();
							ex.setMessage("Cannot convert object of type "
									+ SimpleStoredFileVersionView.class
											.getName() + " to string");
							throw ex;
						}
					}
				});

		// Component
		binder.registerCustomEditor(ComponentCatalogView.class,
				new PropertyEditorSupport() {
					Object value;
					ObjectMapper mapper = new ObjectMapper();

					@Override
					public Object getValue() {
						return value;
					}

					@Override
					public void setAsText(String text)
							throws IllegalArgumentException {
						try {
							value = mapper.readValue(text,
									ComponentCatalogView.class);
						} catch (IOException e) {
							MappingException ex = new MappingException();
							ex.setMessage("Cannot convert string " + text
									+ " to object class "
									+ ComponentCatalogView.class.getName());
							throw ex;
						}
					}

					@Override
					public String getAsText() {
						try {
							return mapper.writeValueAsString(value);
						} catch (JsonProcessingException e) {
							MappingException ex = new MappingException();
							ex.setMessage("Cannot convert object of type "
									+ ComponentCatalogView.class.getName()
									+ " to string");
							throw ex;
						}
					}
				});
		binder.registerCustomEditor(ComponentCategoryView.class,
				new PropertyEditorSupport() {
					Object value;
					ObjectMapper mapper = new ObjectMapper();

					@Override
					public Object getValue() {
						return value;
					}

					@Override
					public void setAsText(String text)
							throws IllegalArgumentException {
						try {
							value = mapper.readValue(text,
									ComponentCategoryView.class);
						} catch (IOException e) {
							MappingException ex = new MappingException();
							ex.setMessage("Cannot convert string " + text
									+ " to object class "
									+ ComponentCategoryView.class.getName());
							throw ex;
						}
					}

					@Override
					public String getAsText() {
						try {
							return mapper.writeValueAsString(value);
						} catch (JsonProcessingException e) {
							MappingException ex = new MappingException();
							ex.setMessage("Cannot convert object of type "
									+ ComponentCategoryView.class.getName()
									+ " to string");
							throw ex;
						}
					}
				});
		binder.registerCustomEditor(ComponentVersionInfoView.class,
				new PropertyEditorSupport() {
					Object value;
					ObjectMapper mapper = new ObjectMapper();

					@Override
					public Object getValue() {
						return value;
					}

					@Override
					public void setAsText(String text)
							throws IllegalArgumentException {
						try {
							value = mapper.readValue(text,
									ComponentVersionInfoView.class);
						} catch (IOException e) {
							MappingException ex = new MappingException();
							ex.setMessage("Cannot convert string " + text
									+ " to object class "
									+ ComponentVersionInfoView.class.getName());
							throw ex;
						}
					}

					@Override
					public String getAsText() {
						try {
							return mapper.writeValueAsString(value);
						} catch (JsonProcessingException e) {
							MappingException ex = new MappingException();
							ex.setMessage("Cannot convert object of type "
									+ ComponentVersionInfoView.class.getName()
									+ " to string");
							throw ex;
						}
					}
				});
		binder.registerCustomEditor(ImageGallery.class,
				new PropertyEditorSupport() {
					Object value;
					ObjectMapper mapper = new ObjectMapper();

					@Override
					public Object getValue() {
						return value;
					}

					@Override
					public void setAsText(String text)
							throws IllegalArgumentException {
						try {
							value = mapper.readValue(text, ImageGallery.class);
						} catch (IOException e) {
							MappingException ex = new MappingException();
							ex.setMessage("Cannot convert string " + text
									+ " to object class "
									+ ImageGallery.class.getName());
							throw ex;
						}
					}

					@Override
					public String getAsText() {
						try {
							return mapper.writeValueAsString(value);
						} catch (JsonProcessingException e) {
							MappingException ex = new MappingException();
							ex.setMessage("Cannot convert object of type "
									+ ImageGallery.class.getName()
									+ " to string");
							throw ex;
						}
					}
				});
		binder.registerCustomEditor(ObsolescenceStrategyView.class,
				new PropertyEditorSupport() {
					Object value;
					ObjectMapper mapper = new ObjectMapper();

					@Override
					public Object getValue() {
						return value;
					}

					@Override
					public void setAsText(String text)
							throws IllegalArgumentException {
						try {
							value = mapper.readValue(text,
									ObsolescenceStrategyView.class);
						} catch (IOException e) {
							MappingException ex = new MappingException();
							ex.setMessage("Cannot convert string " + text
									+ " to object class "
									+ ObsolescenceStrategyView.class.getName());
							throw ex;
						}
					}

					@Override
					public String getAsText() {
						try {
							return mapper.writeValueAsString(value);
						} catch (JsonProcessingException e) {
							MappingException ex = new MappingException();
							ex.setMessage("Cannot convert object of type "
									+ ObsolescenceStrategyView.class.getName()
									+ " to string");
							throw ex;
						}
					}
				});
		binder.registerCustomEditor(TechnicalComponentView.class,
				new PropertyEditorSupport() {
					Object value;
					ObjectMapper mapper = new ObjectMapper();

					@Override
					public Object getValue() {
						return value;
					}

					@Override
					public void setAsText(String text)
							throws IllegalArgumentException {
						try {
							value = mapper.readValue(text,
									TechnicalComponentView.class);
						} catch (IOException e) {
							MappingException ex = new MappingException();
							ex.setMessage("Cannot convert string " + text
									+ " to object class "
									+ TechnicalComponentView.class.getName());
							throw ex;
						}
					}

					@Override
					public String getAsText() {
						try {
							return mapper.writeValueAsString(value);
						} catch (JsonProcessingException e) {
							MappingException ex = new MappingException();
							ex.setMessage("Cannot convert object of type "
									+ TechnicalComponentView.class.getName()
									+ " to string");
							throw ex;
						}
					}
				});

		// Application
		binder.registerCustomEditor(ApplicationVersionView.class,
				new PropertyEditorSupport() {
					Object value;
					ObjectMapper mapper = new ObjectMapper();

					@Override
					public Object getValue() {
						return value;
					}

					@Override
					public void setAsText(String text)
							throws IllegalArgumentException {
						try {
							value = mapper.readValue(text,
									ApplicationVersionView.class);
						} catch (IOException e) {
							MappingException ex = new MappingException();
							ex.setMessage("Cannot convert string " + text
									+ " to object class "
									+ ApplicationVersionView.class.getName());
							throw ex;
						}
					}

					@Override
					public String getAsText() {
						try {
							return mapper.writeValueAsString(value);
						} catch (JsonProcessingException e) {
							MappingException ex = new MappingException();
							ex.setMessage("Cannot convert object of type "
									+ ApplicationVersionView.class.getName()
									+ " to string");
							throw ex;
						}
					}
				});
		binder.registerCustomEditor(BuiltOnView.class,
				new PropertyEditorSupport() {
					Object value;
					ObjectMapper mapper = new ObjectMapper();

					@Override
					public Object getValue() {
						return value;
					}

					@Override
					public void setAsText(String text)
							throws IllegalArgumentException {
						try {
							value = mapper.readValue(text, BuiltOnView.class);
						} catch (IOException e) {
							MappingException ex = new MappingException();
							ex.setMessage("Cannot convert string " + text
									+ " to object class "
									+ BuiltOnView.class.getName());
							throw ex;
						}
					}

					@Override
					public String getAsText() {
						try {
							return mapper.writeValueAsString(value);
						} catch (JsonProcessingException e) {
							MappingException ex = new MappingException();
							ex.setMessage("Cannot convert object of type "
									+ BuiltOnView.class.getName()
									+ " to string");
							throw ex;
						}
					}
				});
		binder.registerCustomEditor(CanRunOnView.class,
				new PropertyEditorSupport() {
					Object value;
					ObjectMapper mapper = new ObjectMapper();

					@Override
					public Object getValue() {
						return value;
					}

					@Override
					public void setAsText(String text)
							throws IllegalArgumentException {
						try {
							value = mapper.readValue(text, CanRunOnView.class);
						} catch (IOException e) {
							MappingException ex = new MappingException();
							ex.setMessage("Cannot convert string " + text
									+ " to object class "
									+ CanRunOnView.class.getName());
							throw ex;
						}
					}

					@Override
					public String getAsText() {
						try {
							return mapper.writeValueAsString(value);
						} catch (JsonProcessingException e) {
							MappingException ex = new MappingException();
							ex.setMessage("Cannot convert object of type "
									+ CanRunOnView.class.getName()
									+ " to string");
							throw ex;
						}
					}
				});
	}
}
