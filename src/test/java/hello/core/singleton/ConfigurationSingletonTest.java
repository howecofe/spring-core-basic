package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        // getMemberRepository() 메소드 사용하기 위해 Impl로 꺼냄. (원래는 구체 타입으로 꺼내면 좋지 않다.)
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        // 셋 다 같음.
        System.out.println("memberService -> memberRepository = " + memberRepository1);
        System.out.println("orderService -> memberRepository = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }

    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass());
        // bean = class hello.core.AppConfig$$EnhancerBySpringCGLIB$$2cd7a939

        // -> 클래스명에 xxxCGLIB가 붙으면서 상당히 복잡해졌다. 이것은 내가 만든 클래스가 아니라, 스프링이 "CGLIB"라는 "바이트코드 조작 라이브러리"를 사용해서
        // AppConfig 클래스를 상속받은 임의의 다른 클래스를 만들고, 그 다른 클래스를 스프링 빈으로 등록한 것이다.
        // 그 임의의 다른 클래스가 바로 싱글톤이 보장되도록 해준다!!

        // @Configuration을 붙이면?
        // @Bean이 붙은 메서드마다 이미 스프링 빈이 존재하면 존재하는 빈을 반환하고, 스프링 빈이 없으면 생성해서 스프링 빈으로 등록하고 반환하는 코드가 동적으로 만들어진다.
        // 이 덕분에 싱글톤이 보장되는 것이다!!!

        // @Bean만 사용해도 스프링 빈으로 등록되지만, 싱글톤은 보장하지 않는다.
        // 따라서 스프링 설정 정보는 항상 @Configuration을 사용하자 !!!
    }
}
