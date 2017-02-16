package sic.integration;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import sic.builder.UsuarioBuilder;
import sic.controller.UsuarioController;
import sic.modelo.Usuario;
import sic.service.IUsuarioService;

@RunWith(SpringRunner.class)
@WebMvcTest(UsuarioController.class)
//@JsonTest
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class mvcTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IUsuarioService usuarioService;

    @Autowired
//    private JacksonTester<Usuario> json;

    //@Before
    //public void setup() {

    //}

    @Test
    public void getVehicleShouldReturnMakeAndModel() throws Exception {
//        MvcResult result = this.mvc.perform(get("/api/v1/usuarios")
//                .accept(MediaType.APPLICATION_JSON))
//                //                  .content(this.json.write(new UsuarioBuilder().build()).getJson().getBytes())                     
//                .andExpect(status().isOk())
//                .andExpect(content().json("[]"))
//                .andReturn();
//
//        String a = json.write(new UsuarioBuilder().build()).getJson();

        //String a = result.getResponse().getContentAsString();
//        given(this.userVehicleService.getVehicleDetails("sboot"))
//            .willReturn(new VehicleDetails("Honda", "Civic"));
//
//        this.mvc.perform(get("/sboot/vehicle")
//            .accept(MediaType.TEXT_PLAIN))
//            .andExpect(status().isOk())
//            .andExpect(content().string("Honda Civic"));
    }

}
