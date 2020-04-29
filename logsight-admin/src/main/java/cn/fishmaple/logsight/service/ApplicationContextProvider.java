package cn.fishmaple.logsight.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider
        implements ApplicationContextAware {
    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 获取applicationContext
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean.
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        if(null==getApplicationContext()){
                return null;
        }
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    public static void printAllBeans(ApplicationContext applicationContext) {
        applicationContext=(applicationContext==null?getApplicationContext():applicationContext);
        String[] beans = applicationContext
                .getBeanDefinitionNames();
        System.out.println("***********************包加载开始******************************");
        for (String beanName : beans) {
            Class<?> beanType = applicationContext
                    .getType(beanName);

            System.out.println("BeanName:" + beanName+//"     Bean的类型：" + beanType+
                    "     Bean所在的包：" + beanType.getPackage());

        }System.out.println("***********************包加载完毕******************************");
    }
}
