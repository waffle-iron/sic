package sic.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import sic.builder.UsuarioBuilder;
import sic.controller.UsuarioController;
import sic.modelo.Usuario;
import sic.service.IUsuarioService;

@RunWith(SpringRunner.class)
@WebMvcTest(UsuarioController.class)
public class mvcTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IUsuarioService usuarioService;
    
    private JacksonTester<Usuario> json;

    @Before
    public void setup() {        
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void getVehicleShouldReturnMakeAndModel() throws Exception {
        MvcResult result = mvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.write(new UsuarioBuilder().build()).getJson()))                     
                .andExpect(status().isCreated())
                .andReturn();        

        String a = result.getResponse().getContentAsString();
    }

}
