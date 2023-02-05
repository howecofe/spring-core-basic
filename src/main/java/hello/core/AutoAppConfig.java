package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

// @ComponentScan: @Component가 붙은 클래스를 찾아 자동으로 스프링 빈으로 등록
// @Configuration에는 @Component 애노테이션이 포함되어 있다.
// 그래서 @ComponentScan을 사용하면 @Configuration이 붙은 설정 정보도 자동으로 등록되기 때문에, 앞서 만들어두었던 AppConfig, TestConfig 등의 설정 정보도 함께 등록되어 실행된다.
// 따라서 excludeFilters를 이용하여, 설정 정보는 컴포넌트 스캔 대상에서 제외했다. (보통 스캔 대상에서 제외하지는 않지만, 기존 예제 코드를 활용하기 위해 이 방법 선택)
@Configuration
@ComponentScan(
//        basePackages = "hello.core.member", // 탐색할 패키지의 시작 위치 지정, 이 패키지를 포함해서 하위 패키지 모두 탐색
//        basePackageClasses = AutoAppConfig.class, // 지정한 클래스의 패키지를 탐색 시작 위치로 지정, 여기서는 hello.core
//        basePackages를 지정하지 않으면, @ComponentScan이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.
        excludeFilters= @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
}
