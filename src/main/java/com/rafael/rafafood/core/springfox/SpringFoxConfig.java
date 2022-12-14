package com.rafael.rafafood.core.springfox;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.classmate.TypeResolver;
import com.rafael.rafafood.api.exceptionhandler.Problem;
import com.rafael.rafafood.api.v1.model.CidadeModel;
import com.rafael.rafafood.api.v1.model.CozinhaModel;
import com.rafael.rafafood.api.v1.model.EstadoModel;
import com.rafael.rafafood.api.v1.model.FormaPagamentoModel;
import com.rafael.rafafood.api.v1.model.GrupoModel;
import com.rafael.rafafood.api.v1.model.PedidoResumoModel;
import com.rafael.rafafood.api.v1.model.PermissaoModel;
import com.rafael.rafafood.api.v1.model.ProdutoModel;
import com.rafael.rafafood.api.v1.model.RestauranteBasicoModel;
import com.rafael.rafafood.api.v1.model.UsuarioModel;
import com.rafael.rafafood.api.v1.openapi.model.CidadesModelOpenApi;
import com.rafael.rafafood.api.v1.openapi.model.CozinhasModelOpenApi;
import com.rafael.rafafood.api.v1.openapi.model.EstadosModelOpenApi;
import com.rafael.rafafood.api.v1.openapi.model.FormasPagamentoModelOpenApi;
import com.rafael.rafafood.api.v1.openapi.model.GruposModelOpenApi;
import com.rafael.rafafood.api.v1.openapi.model.LinksModelOpenApi;
import com.rafael.rafafood.api.v1.openapi.model.PageableModelOpenApi;
import com.rafael.rafafood.api.v1.openapi.model.PedidosResumoModelOpenApi;
import com.rafael.rafafood.api.v1.openapi.model.PermissoesModelOpenApi;
import com.rafael.rafafood.api.v1.openapi.model.ProdutosModelOpenApi;
import com.rafael.rafafood.api.v1.openapi.model.RestaurantesBasicoModelOpenApi;
import com.rafael.rafafood.api.v1.openapi.model.UsuariosModelOpenApi;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {

	@Bean
	public Docket apiDocketV1() {
		var typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("V1")
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.rafael.rafafood.api"))
					.paths(PathSelectors.ant("/v1/**"))
					.build()
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
				.globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
				.globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
				.globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
				.additionalModels(typeResolver.resolve(Problem.class))
				.ignoredParameterTypes(ServletWebRequest.class,
						URL.class, URI.class, URLStreamHandler.class, Resource.class,
						File.class, InputStream.class)
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.directModelSubstitute(Links.class, LinksModelOpenApi.class)
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, CozinhaModel.class),
						CozinhasModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, PedidoResumoModel.class),
						PedidosResumoModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, CidadeModel.class),
						CidadesModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, EstadoModel.class),
						EstadosModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, FormaPagamentoModel.class),
						FormasPagamentoModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, GrupoModel.class),
						GruposModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, PermissaoModel.class),
						PermissoesModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, ProdutoModel.class),
						ProdutosModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, RestauranteBasicoModel.class),
						RestaurantesBasicoModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, UsuarioModel.class),
						UsuariosModelOpenApi.class))
				
				.securitySchemes(Arrays.asList(securityScheme()))
				.securityContexts(Arrays.asList(securityContext()))
				
				.apiInfo(apiInfoV1())
				.tags(new Tag("Cidades", "Gerencia as cidades"),
						new Tag("Grupos", "Gerencia os grupos de usu??rios"),
						new Tag("Cozinhas", "Gerencia as cozinhas"),
						new Tag("Formas de pagamento", "Gerencia as formas de pagamento"),
						new Tag("Pedidos", "Gerencia os pedidos"),
						new Tag("Restaurantes", "Gerencia os restaurantes"),
						new Tag("Estados", "Gerencia os estados"),
						new Tag("Produtos", "Gerencia os produtos de restaurantes"),
						new Tag("Usu??rios", "Gerencia os usu??rios"),
						new Tag("Estat??sticas", "Estat??sticas da Rafafood"),
						new Tag("Permiss??es", "Gerencia as permiss??es"));
	}
	
	private SecurityScheme securityScheme() {
		return new OAuthBuilder()
				.name("Rafafood")
				.grantTypes(grantTypes())
				.scopes(scopes())
				.build();
	}
	
	private SecurityContext securityContext() {
		var securityReference = SecurityReference.builder()
				.reference("Rafafood")
				.scopes(scopes().toArray(new AuthorizationScope[0]))
				.build();
		
		return SecurityContext.builder()
				.securityReferences(Arrays.asList(securityReference))
				.forPaths(PathSelectors.any())
				.build();
	}
	
	private List<GrantType> grantTypes() {
		return Arrays.asList(new ResourceOwnerPasswordCredentialsGrant("/oauth/token"));
	}
	
	private List<AuthorizationScope> scopes() {
		return Arrays.asList(new AuthorizationScope("READ", "Acesso de leitura"),
				new AuthorizationScope("WRITE", "Acesso de escrita"));
	}
	
	private List<ResponseMessage> globalGetResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Erro interno do servidor")
					.responseModel(new ModelRef("Problema"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_ACCEPTABLE.value())
					.message("Recurso n??o possui representa????o que poderia ser aceita pelo consumidor")
					.build()
			);
	}
	
	private List<ResponseMessage> globalPostPutResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.BAD_REQUEST.value())
					.message("Requisi????o inv??lida (erro do cliente)")
					.responseModel(new ModelRef("Problema"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Erro interno no servidor")
					.responseModel(new ModelRef("Problema"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_ACCEPTABLE.value())
					.message("Recurso n??o possui representa????o que poderia ser aceita pelo consumidor")
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
					.message("Requisi????o recusada porque o corpo est?? em um formato n??o suportado")
					.responseModel(new ModelRef("Problema"))
					.build()
			);
	}
	
	private List<ResponseMessage> globalDeleteResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.BAD_REQUEST.value())
					.message("Requisi????o inv??lida (erro do cliente)")
					.responseModel(new ModelRef("Problema"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Erro interno no servidor")
					.responseModel(new ModelRef("Problema"))
					.build()
			);
	}
	
	private ApiInfo apiInfoV1() {
		return new ApiInfoBuilder()
				.title("RafaFood API")
				.description("API aberta para clientes e restaurantes.")
				.version("1")
				.contact(new Contact("Rafael", "https://www.rafael.com", "contato@rafael.com"))
				.build();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
			.addResourceLocations("classpath:/META-INF/resources/");
		
		registry.addResourceHandler("/webjars/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
}
