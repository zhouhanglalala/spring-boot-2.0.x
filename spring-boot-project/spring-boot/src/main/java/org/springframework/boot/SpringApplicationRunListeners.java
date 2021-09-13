/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.ReflectionUtils;

/**
 * A collection of {@link SpringApplicationRunListener}.
 *
 * @author Phillip Webb
 */
class SpringApplicationRunListeners {

	private final Log log;

	private final List<SpringApplicationRunListener> listeners;

	SpringApplicationRunListeners(Log log, Collection<? extends SpringApplicationRunListener> listeners) {
		this.log = log;
		this.listeners = new ArrayList<>(listeners);
	}

	void starting() {
		// 回调所有监听器的监听方法
		for (SpringApplicationRunListener listener : this.listeners) {
			listener.starting();
		}
	}

	/**
	 * 进去for之后会拿到7个
	 * ConfigFileApplicationListener:
	 * 解析application.properties/yml以及application-profile.properties/yml配置文件核心事件监听器
	 *
	 * AnsiOutputApplicationListener:
	 * 根据spring.output.ansi.enabled配置值配置日志打印色彩模式。
	 *
	 * LoggingApplicationListener:
	 * 解析application.properties/yml配置文件logging开头的配置，并将配置信息初始化日志到系统。
	 *
	 * BackgroundPreinitializer：
	 * 这个监听器不处理当前的事件。
	 *
	 * ClasspathLoggingApplicationListener：
	 * 纯日志打印，启动过程中控制台debug级别的日志关键字“Application started with classpath”相关的信息就是这个监听器打印的。
	 *
	 * DelegatingApplicationListener：
	 * application.properties/yml配置文件context.listener.classes配置的自定义监听器的代理执行者。主要工作是执行自定义配置的事件监听器。
	 *
	 * FileEncodingApplicationListener：
	 * 将application.properties/yml配置文件spring.mandatory-file-encoding配置跟System.getProperty("file.encoding")值进行忽略大小写匹配，
	 * 如果匹配不上，直接报错（throw new IllegalStateException("The Java Virtual Machine has not been configured to use the desired default character encoding (" + desired + ").")）
	 * @param environment
	 */
	void environmentPrepared(ConfigurableEnvironment environment) {
		for (SpringApplicationRunListener listener : this.listeners) {
			listener.environmentPrepared(environment);
		}
	}

	void contextPrepared(ConfigurableApplicationContext context) {
		for (SpringApplicationRunListener listener : this.listeners) {
			listener.contextPrepared(context);
		}
	}

	void contextLoaded(ConfigurableApplicationContext context) {
		for (SpringApplicationRunListener listener : this.listeners) {
			listener.contextLoaded(context);
		}
	}

	void started(ConfigurableApplicationContext context) {
		for (SpringApplicationRunListener listener : this.listeners) {
			listener.started(context);
		}
	}

	void running(ConfigurableApplicationContext context) {
		for (SpringApplicationRunListener listener : this.listeners) {
			listener.running(context);
		}
	}

	void failed(ConfigurableApplicationContext context, Throwable exception) {
		for (SpringApplicationRunListener listener : this.listeners) {
			callFailedListener(listener, context, exception);
		}
	}

	private void callFailedListener(SpringApplicationRunListener listener, ConfigurableApplicationContext context,
			Throwable exception) {
		try {
			listener.failed(context, exception);
		}
		catch (Throwable ex) {
			if (exception == null) {
				ReflectionUtils.rethrowRuntimeException(ex);
			}
			if (this.log.isDebugEnabled()) {
				this.log.error("Error handling failed", ex);
			}
			else {
				String message = ex.getMessage();
				message = (message != null) ? message : "no error message";
				this.log.warn("Error handling failed (" + message + ")");
			}
		}
	}

}
