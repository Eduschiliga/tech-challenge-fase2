package br.com.fiap.techchallengefase2.infra.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.web.method.HandlerMethod;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SwaggerConfigTest {

    private final SwaggerConfig swaggerConfig = new SwaggerConfig();

    @Test
    void customOpenAPI_DeveConfigurarSecuritySchemeCorretamente() {
        OpenAPI openAPI = swaggerConfig.customOpenAPI();

        assertNotNull(openAPI.getComponents());
        SecurityScheme scheme = openAPI.getComponents().getSecuritySchemes().get("bearerAuth");

        assertNotNull(scheme);
        assertEquals(SecurityScheme.Type.HTTP, scheme.getType());
        assertEquals("bearer", scheme.getScheme());
        assertEquals("JWT", scheme.getBearerFormat());

        List<SecurityRequirement> security = openAPI.getSecurity();
        assertNotNull(security);
        assertTrue(security.get(0).containsKey("bearerAuth"));
    }

    @Test
    void hideUsuarioLogadoIdHeader_DeveRemoverParametroEspecifico() {
        OperationCustomizer customizer = swaggerConfig.hideUsuarioLogadoIdHeader();
        Operation operation = new Operation();
        List<Parameter> parameters = new ArrayList<>();

        Parameter p1 = new Parameter().name("x-usuario-logado-cardapioId").in("header");
        Parameter p2 = new Parameter().name("outro-header").in("header");

        parameters.add(p1);
        parameters.add(p2);
        operation.setParameters(parameters);

        customizer.customize(operation, mock(HandlerMethod.class));

        assertEquals(1, operation.getParameters().size());
        assertEquals("outro-header", operation.getParameters().get(0).getName());
        assertFalse(operation.getParameters().stream()
                .anyMatch(p -> "x-usuario-logado-cardapioId".equals(p.getName())));
    }

    @Test
    void hideUsuarioLogadoIdHeader_NaoDeveFalharQuandoParametrosForemNulos() {
        OperationCustomizer customizer = swaggerConfig.hideUsuarioLogadoIdHeader();
        Operation operation = new Operation();
        operation.setParameters(null);

        assertDoesNotThrow(() -> customizer.customize(operation, mock(HandlerMethod.class)));
    }
}