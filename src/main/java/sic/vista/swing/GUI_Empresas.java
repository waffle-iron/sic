package sic.vista.swing;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.PersistenceException;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import sic.AppContextProvider;
import sic.modelo.CondicionIVA;
import sic.modelo.Empresa;
import sic.modelo.Localidad;
import sic.modelo.Pais;
import sic.modelo.Provincia;
import sic.service.ICondicionIVAService;
import sic.service.IEmpresaService;
import sic.service.ILocalidadService;
import sic.service.IPaisService;
import sic.service.IProvinciaService;
import sic.service.ServiceException;
import sic.service.TipoDeOperacion;
import sic.util.FiltroImagenes;
import sic.util.Utilidades;
import sic.util.Validator;

public class GUI_Empresas extends JDialog {

    private final DefaultListModel modeloListEmpresas = new DefaultListModel();
    private Empresa empresaSeleccionada;
    private TipoDeOperacion tipoDeOperacion;
    private byte[] logo = null;
    private final ApplicationContext appContext = AppContextProvider.getApplicationContext();
    private final ICondicionIVAService condicionIVAService = appContext.getBean(ICondicionIVAService.class);
    private final IPaisService paisService = appContext.getBean(IPaisService.class);
    private final IProvinciaService provinciaService = appContext.getBean(IProvinciaService.class);
    private final ILocalidadService localidadService = appContext.getBean(ILocalidadService.class);
    private final IEmpresaService empresaService = appContext.getBean(IEmpresaService.class);
    private static final Logger log = Logger.getLogger(GUI_Empresas.class.getPackage().getName());

    public GUI_Empresas() {
        this.initComponents();
        this.setIcon();
        panel2.setVisible(false);
        btn_Guardar.setVisible(false);
        btn_Cancelar.setVisible(false);
        btn_ExaminarArchivos.setVisible(false);
        btn_EliminarLogo.setVisible(false);
        lbl_Logo.setVisible(false);
        this.setSize(415, 190);
    }

    private void setIcon() {
        ImageIcon iconoVentana = new ImageIcon(GUI_DetalleCliente.class.getResource("/sic/icons/Empresa_16x16.png"));
        this.setIconImage(iconoVentana.getImage());
    }

    private void centrarInternalFrame() {
        this.setLocationRelativeTo(this.getParent());
    }

    private void desplegarDetalle() {
        btn_NuevaEmpresa.setEnabled(false);
        btn_ModificarEmpresa.setEnabled(false);
        btn_EliminarEmpresa.setEnabled(false);
        lst_Empresas.setEnabled(false);
        panel2.setVisible(true);
        this.setSize(610, 605);
        btn_Guardar.setVisible(true);
        btn_Cancelar.setVisible(true);
        centrarInternalFrame();
        txt_Nombre.setEnabled(true);
        txt_Lema.setEnabled(true);
        txt_Direccion.setEnabled(true);
        cmb_CondicionIVA.setEnabled(true);
        btn_NuevaCondicionIVA.setEnabled(true);
        txt_CUIP.setEnabled(true);
        txt_IngBrutos.setEnabled(true);
        dc_FechaInicioActividad.setEnabled(true);
        txt_Email.setEnabled(true);
        txt_Telefono.setEnabled(true);
        cmb_Pais.setEnabled(true);
        cmb_Provincia.setEnabled(true);
        cmb_Localidad.setEnabled(true);
        btn_NuevoPais.setEnabled(true);
        btn_NuevaProvincia.setEnabled(true);
        btn_NuevaLocalidad.setEnabled(true);
        btn_Guardar.setEnabled(true);
        btn_Cancelar.setEnabled(true);
        btn_ExaminarArchivos.setEnabled(true);
        btn_EliminarLogo.setEnabled(true);
        btn_ExaminarArchivos.setVisible(true);
        btn_EliminarLogo.setVisible(true);
        lbl_Logo.setVisible(true);
    }

    private void plegarDetalle() {
        btn_NuevaEmpresa.setEnabled(true);
        btn_ModificarEmpresa.setEnabled(true);
        btn_EliminarEmpresa.setEnabled(true);
        lst_Empresas.setEnabled(true);
        panel2.setVisible(false);
        this.setSize(415, 190);
        btn_Guardar.setVisible(false);
        btn_Cancelar.setVisible(false);
        centrarInternalFrame();
        txt_Nombre.setEnabled(false);
        txt_Lema.setEnabled(false);
        txt_Direccion.setEnabled(false);
        cmb_CondicionIVA.setEnabled(false);
        btn_NuevaCondicionIVA.setEnabled(false);
        txt_CUIP.setEnabled(false);
        txt_IngBrutos.setEnabled(false);
        dc_FechaInicioActividad.setEnabled(false);
        txt_Email.setEnabled(false);
        txt_Telefono.setEnabled(false);
        cmb_Pais.setEnabled(false);
        cmb_Provincia.setEnabled(false);
        cmb_Localidad.setEnabled(false);
        btn_NuevoPais.setEnabled(false);
        btn_NuevaProvincia.setEnabled(false);
        btn_NuevaLocalidad.setEnabled(false);
        btn_Guardar.setEnabled(false);
        btn_Cancelar.setEnabled(false);
        btn_ExaminarArchivos.setEnabled(false);
        btn_EliminarLogo.setEnabled(false);
        btn_ExaminarArchivos.setVisible(false);
        btn_EliminarLogo.setVisible(false);
        lbl_Logo.setVisible(false);
    }

    private void limpiarYRecargarComponentes() {
        txt_Nombre.setText("");
        txt_Lema.setText("");
        txt_Direccion.setText("");
        txt_CUIP.setText("");
        txt_IngBrutos.setText("");
        dc_FechaInicioActividad.cleanup();
        txt_Email.setText("");
        txt_Telefono.setText("");
        lbl_Logo.setIcon(null);
        lbl_Logo.setText("SIN IMAGEN");
        logo = null;
    }

    private void cargarComboBoxCondicionesIVA() {
        List<CondicionIVA> condicionesIVA;
        cmb_CondicionIVA.removeAllItems();
        condicionesIVA = condicionIVAService.getCondicionesIVA();
        for (CondicionIVA cond : condicionesIVA) {
            cmb_CondicionIVA.addItem(cond);
        }
    }

    private void cargarComboBoxPaises() {
        cmb_Pais.removeAllItems();
        List<Pais> paises = paisService.getPaises();
        for (Pais pais : paises) {
            cmb_Pais.addItem(pais);
        }
    }

    private void cargarComboBoxProvinciasDelPais(Pais paisSeleccionado) {
        List<Provincia> provincias;
        cmb_Provincia.removeAllItems();
        provincias = provinciaService.getProvinciasDelPais(paisSeleccionado);
        for (Provincia prov : provincias) {
            cmb_Provincia.addItem(prov);
        }
    }

    private void cargarComboBoxLocalidadesDeLaProvincia(Provincia provSeleccionada) {
        List<Localidad> Localidades;
        cmb_Localidad.removeAllItems();
        Localidades = localidadService.getLocalidadesDeLaProvincia(provSeleccionada);
        for (Localidad loc : Localidades) {
            cmb_Localidad.addItem(loc);
        }
    }

    private void cargarListEmpresas() {
        modeloListEmpresas.removeAllElements();
        List<Empresa> empresas = empresaService.getEmpresas();
        for (Empresa empresa : empresas) {
            modeloListEmpresas.addElement(empresa);
        }
        lst_Empresas.setModel(modeloListEmpresas);
    }

    private void cargarEmpresaParaModificar() {
        txt_Nombre.setText(empresaSeleccionada.getNombre());
        txt_Lema.setText(empresaSeleccionada.getLema());
        txt_Direccion.setText(empresaSeleccionada.getDireccion());
        cmb_CondicionIVA.setSelectedItem(empresaSeleccionada.getCondicionIVA());

        if (empresaSeleccionada.getCuip() == 0) {
            txt_CUIP.setText("");
        } else {
            txt_CUIP.setText(String.valueOf(empresaSeleccionada.getCuip()));
        }

        if (empresaSeleccionada.getIngresosBrutos() == 0) {
            txt_IngBrutos.setText("");
        } else {
            txt_IngBrutos.setText(String.valueOf(empresaSeleccionada.getIngresosBrutos()));
        }

        dc_FechaInicioActividad.setDate(empresaSeleccionada.getFechaInicioActividad());
        txt_Email.setText(empresaSeleccionada.getEmail());
        txt_Telefono.setText(empresaSeleccionada.getTelefono());
        cmb_Pais.setSelectedItem(empresaSeleccionada.getLocalidad().getProvincia().getPais());
        cmb_Provincia.setSelectedItem(empresaSeleccionada.getLocalidad().getProvincia());
        cmb_Localidad.setSelectedItem(empresaSeleccionada.getLocalidad());

        if (empresaSeleccionada.getLogo() == null) {
            lbl_Logo.setText("SIN IMAGEN");
            logo = null;
        } else {
            lbl_Logo.setText("");
            logo = empresaSeleccionada.getLogo();
            ImageIcon imagenLogo = new ImageIcon(logo);
            ImageIcon logoRedimensionado = new ImageIcon(imagenLogo.getImage().getScaledInstance(114, 114, Image.SCALE_SMOOTH));
            lbl_Logo.setIcon(logoRedimensionado);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton7 = new javax.swing.JButton();
        panel1 = new javax.swing.JPanel();
        sp_Empresas = new javax.swing.JScrollPane();
        lst_Empresas = new javax.swing.JList();
        lbl_MisEmpresas = new javax.swing.JLabel();
        btn_NuevaEmpresa = new javax.swing.JButton();
        btn_ModificarEmpresa = new javax.swing.JButton();
        btn_EliminarEmpresa = new javax.swing.JButton();
        lbl_Logo = new javax.swing.JLabel();
        btn_ExaminarArchivos = new javax.swing.JButton();
        btn_EliminarLogo = new javax.swing.JButton();
        panel2 = new javax.swing.JPanel();
        lbl_Nombre = new javax.swing.JLabel();
        lbl_Direccion = new javax.swing.JLabel();
        lbl_Lema = new javax.swing.JLabel();
        lbl_CondicionIVA = new javax.swing.JLabel();
        lbl_CUIP = new javax.swing.JLabel();
        lbl_IngBrutos = new javax.swing.JLabel();
        lbl_FIA = new javax.swing.JLabel();
        lbl_Email = new javax.swing.JLabel();
        lbl_Telefono = new javax.swing.JLabel();
        lbl_Pais = new javax.swing.JLabel();
        lbl_Provincia = new javax.swing.JLabel();
        lbl_Localidad = new javax.swing.JLabel();
        txt_Nombre = new javax.swing.JTextField();
        txt_Lema = new javax.swing.JTextField();
        txt_Direccion = new javax.swing.JTextField();
        txt_CUIP = new javax.swing.JTextField();
        txt_IngBrutos = new javax.swing.JTextField();
        txt_Email = new javax.swing.JTextField();
        txt_Telefono = new javax.swing.JTextField();
        cmb_CondicionIVA = new javax.swing.JComboBox();
        cmb_Pais = new javax.swing.JComboBox();
        cmb_Provincia = new javax.swing.JComboBox();
        cmb_Localidad = new javax.swing.JComboBox();
        btn_NuevoPais = new javax.swing.JButton();
        btn_NuevaProvincia = new javax.swing.JButton();
        btn_NuevaLocalidad = new javax.swing.JButton();
        btn_NuevaCondicionIVA = new javax.swing.JButton();
        dc_FechaInicioActividad = new com.toedter.calendar.JDateChooser();
        btn_Cancelar = new javax.swing.JButton();
        btn_Guardar = new javax.swing.JButton();

        jButton7.setText("jButton7");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Empresas");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lst_Empresas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lst_Empresas.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lst_EmpresasValueChanged(evt);
            }
        });
        sp_Empresas.setViewportView(lst_Empresas);

        lbl_MisEmpresas.setText("Mis Empresas:");

        btn_NuevaEmpresa.setForeground(java.awt.Color.blue);
        btn_NuevaEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/AddEmpresa_16x16.png"))); // NOI18N
        btn_NuevaEmpresa.setText("Nueva");
        btn_NuevaEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NuevaEmpresaActionPerformed(evt);
            }
        });

        btn_ModificarEmpresa.setForeground(java.awt.Color.blue);
        btn_ModificarEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/EditEmpresa_16x16.png"))); // NOI18N
        btn_ModificarEmpresa.setText("Modificar");
        btn_ModificarEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ModificarEmpresaActionPerformed(evt);
            }
        });

        btn_EliminarEmpresa.setForeground(java.awt.Color.blue);
        btn_EliminarEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/RemoveEmpresa_16x16.png"))); // NOI18N
        btn_EliminarEmpresa.setText("Eliminar");
        btn_EliminarEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EliminarEmpresaActionPerformed(evt);
            }
        });

        lbl_Logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Logo.setText("SIN IMAGEN");
        lbl_Logo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lbl_Logo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btn_ExaminarArchivos.setForeground(java.awt.Color.blue);
        btn_ExaminarArchivos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/AddPicture_16x16.png"))); // NOI18N
        btn_ExaminarArchivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ExaminarArchivosActionPerformed(evt);
            }
        });

        btn_EliminarLogo.setForeground(java.awt.Color.blue);
        btn_EliminarLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/RemovePicture_16x16.png"))); // NOI18N
        btn_EliminarLogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EliminarLogoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_MisEmpresas)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(sp_Empresas, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_EliminarEmpresa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                            .addComponent(btn_ModificarEmpresa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                            .addComponent(btn_NuevaEmpresa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_Logo, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_ExaminarArchivos)
                    .addComponent(btn_EliminarLogo)))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addComponent(lbl_MisEmpresas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(btn_ExaminarArchivos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_EliminarLogo))
                    .addComponent(lbl_Logo, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                    .addComponent(sp_Empresas, 0, 0, Short.MAX_VALUE)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(btn_NuevaEmpresa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_ModificarEmpresa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_EliminarEmpresa)))
                .addContainerGap())
        );

        panel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_EliminarEmpresa, btn_ModificarEmpresa, btn_NuevaEmpresa});

        panel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lbl_Nombre.setForeground(java.awt.Color.red);
        lbl_Nombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Nombre.setText("* Nombre:");

        lbl_Direccion.setForeground(java.awt.Color.red);
        lbl_Direccion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Direccion.setText("* Dirección:");

        lbl_Lema.setText("Lema:");

        lbl_CondicionIVA.setForeground(java.awt.Color.red);
        lbl_CondicionIVA.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_CondicionIVA.setText("* Condición IVA:");

        lbl_CUIP.setText("CUIT / CUIL / CUIP:");

        lbl_IngBrutos.setText("Ing. Brutos:");

        lbl_FIA.setText("Fecha Inicio Actividad:");

        lbl_Email.setText("Correo Electronico:");

        lbl_Telefono.setText("Teléfono:");

        lbl_Pais.setForeground(java.awt.Color.red);
        lbl_Pais.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Pais.setText("* Pais:");

        lbl_Provincia.setForeground(java.awt.Color.red);
        lbl_Provincia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Provincia.setText("* Provincia:");

        lbl_Localidad.setForeground(java.awt.Color.red);
        lbl_Localidad.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_Localidad.setText("* Localidad:");

        txt_Nombre.setEnabled(false);

        txt_Lema.setEnabled(false);

        txt_Direccion.setEnabled(false);

        txt_CUIP.setEnabled(false);

        txt_IngBrutos.setEnabled(false);

        txt_Email.setEnabled(false);

        txt_Telefono.setEnabled(false);

        cmb_CondicionIVA.setEnabled(false);

        cmb_Pais.setEnabled(false);
        cmb_Pais.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_PaisItemStateChanged(evt);
            }
        });

        cmb_Provincia.setEnabled(false);
        cmb_Provincia.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_ProvinciaItemStateChanged(evt);
            }
        });

        cmb_Localidad.setEnabled(false);

        btn_NuevoPais.setForeground(java.awt.Color.blue);
        btn_NuevoPais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/AddMap_16x16.png"))); // NOI18N
        btn_NuevoPais.setText("Nuevo");
        btn_NuevoPais.setEnabled(false);
        btn_NuevoPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NuevoPaisActionPerformed(evt);
            }
        });

        btn_NuevaProvincia.setForeground(java.awt.Color.blue);
        btn_NuevaProvincia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/AddMap_16x16.png"))); // NOI18N
        btn_NuevaProvincia.setText("Nueva");
        btn_NuevaProvincia.setEnabled(false);
        btn_NuevaProvincia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NuevaProvinciaActionPerformed(evt);
            }
        });

        btn_NuevaLocalidad.setForeground(java.awt.Color.blue);
        btn_NuevaLocalidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/AddMap_16x16.png"))); // NOI18N
        btn_NuevaLocalidad.setText("Nueva");
        btn_NuevaLocalidad.setEnabled(false);
        btn_NuevaLocalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NuevaLocalidadActionPerformed(evt);
            }
        });

        btn_NuevaCondicionIVA.setForeground(java.awt.Color.blue);
        btn_NuevaCondicionIVA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/AddMoney_16x16.png"))); // NOI18N
        btn_NuevaCondicionIVA.setText("Nueva");
        btn_NuevaCondicionIVA.setEnabled(false);
        btn_NuevaCondicionIVA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NuevaCondicionIVAActionPerformed(evt);
            }
        });

        dc_FechaInicioActividad.setDateFormatString("dd/MM/yyyy");
        dc_FechaInicioActividad.setEnabled(false);

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_FIA)
                            .addComponent(lbl_CondicionIVA)
                            .addComponent(lbl_CUIP)
                            .addComponent(lbl_IngBrutos)
                            .addComponent(lbl_Lema)
                            .addComponent(lbl_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_Direccion, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                                .addComponent(cmb_CondicionIVA, 0, 296, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_NuevaCondicionIVA))
                            .addComponent(txt_Nombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                            .addComponent(txt_Lema, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                            .addComponent(txt_Direccion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                            .addComponent(txt_CUIP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                            .addComponent(txt_IngBrutos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                            .addComponent(dc_FechaInicioActividad, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_Localidad)
                            .addComponent(lbl_Provincia)
                            .addComponent(lbl_Pais)
                            .addComponent(lbl_Telefono)
                            .addComponent(lbl_Email))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addComponent(cmb_Localidad, 0, 290, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_NuevaLocalidad))
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addComponent(cmb_Provincia, 0, 290, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_NuevaProvincia))
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addComponent(cmb_Pais, 0, 289, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_NuevoPais))
                            .addComponent(txt_Telefono, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                            .addComponent(txt_Email, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Nombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Lema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Lema))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Direccion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_CondicionIVA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_NuevaCondicionIVA)
                    .addComponent(lbl_CondicionIVA))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_CUIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_CUIP))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_IngBrutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_IngBrutos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(dc_FechaInicioActividad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_FIA))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Email))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Telefono))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_Pais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_NuevoPais)
                    .addComponent(lbl_Pais))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_Provincia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_NuevaProvincia)
                    .addComponent(lbl_Provincia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_Localidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_NuevaLocalidad)
                    .addComponent(lbl_Localidad))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_Cancelar.setForeground(java.awt.Color.blue);
        btn_Cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/Cancel_16x16.png"))); // NOI18N
        btn_Cancelar.setText("Cancelar");
        btn_Cancelar.setEnabled(false);
        btn_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CancelarActionPerformed(evt);
            }
        });

        btn_Guardar.setForeground(java.awt.Color.blue);
        btn_Guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sic/icons/Accept_16x16.png"))); // NOI18N
        btn_Guardar.setText("Guardar");
        btn_Guardar.setEnabled(false);
        btn_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_GuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btn_Guardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Cancelar))
                    .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Cancelar)
                    .addComponent(btn_Guardar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ModificarEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ModificarEmpresaActionPerformed
        if (empresaSeleccionada != null) {
            desplegarDetalle();
            tipoDeOperacion = TipoDeOperacion.ACTUALIZACION;
            txt_Nombre.requestFocus();
            cargarEmpresaParaModificar();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una empresa para poder modificarla.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
}//GEN-LAST:event_btn_ModificarEmpresaActionPerformed

    private void btn_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CancelarActionPerformed
        plegarDetalle();
        tipoDeOperacion = null;
        limpiarYRecargarComponentes();
    }//GEN-LAST:event_btn_CancelarActionPerformed

    private void lst_EmpresasValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lst_EmpresasValueChanged
        empresaSeleccionada = (Empresa) lst_Empresas.getSelectedValue();
    }//GEN-LAST:event_lst_EmpresasValueChanged

    private void cmb_PaisItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_PaisItemStateChanged
        if (cmb_Pais.getItemCount() > 0) {
            try {
                cargarComboBoxProvinciasDelPais((Pais) cmb_Pais.getSelectedItem());

            } catch (PersistenceException ex) {
                log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
                JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_cmb_PaisItemStateChanged

    private void cmb_ProvinciaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_ProvinciaItemStateChanged
        if (cmb_Provincia.getItemCount() > 0) {
            try {
                cargarComboBoxLocalidadesDeLaProvincia((Provincia) cmb_Provincia.getSelectedItem());

            } catch (PersistenceException ex) {
                log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
                JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            cmb_Localidad.removeAllItems();
        }
    }//GEN-LAST:event_cmb_ProvinciaItemStateChanged

    private void btn_NuevaEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NuevaEmpresaActionPerformed
        desplegarDetalle();
        tipoDeOperacion = TipoDeOperacion.ALTA;
        txt_Nombre.requestFocus();
    }//GEN-LAST:event_btn_NuevaEmpresaActionPerformed

    private void btn_NuevaCondicionIVAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NuevaCondicionIVAActionPerformed
        GUI_DetalleCondicionIVA gui_DetalleCondicionIVA = new GUI_DetalleCondicionIVA();
        gui_DetalleCondicionIVA.setModal(true);
        gui_DetalleCondicionIVA.setLocationRelativeTo(this);
        gui_DetalleCondicionIVA.setVisible(true);

        try {
            cargarComboBoxCondicionesIVA();

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_NuevaCondicionIVAActionPerformed

    private void btn_NuevoPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NuevoPaisActionPerformed
        GUI_DetallePais gui_DetallePais = new GUI_DetallePais();
        gui_DetallePais.setModal(true);
        gui_DetallePais.setLocationRelativeTo(this);
        gui_DetallePais.setVisible(true);

        try {
            cargarComboBoxPaises();

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_NuevoPaisActionPerformed

    private void btn_NuevaProvinciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NuevaProvinciaActionPerformed
        GUI_DetalleProvincia gui_DetalleProvincia = new GUI_DetalleProvincia();
        gui_DetalleProvincia.setModal(true);
        gui_DetalleProvincia.setLocationRelativeTo(this);
        gui_DetalleProvincia.setVisible(true);

        try {
            cargarComboBoxProvinciasDelPais((Pais) cmb_Pais.getSelectedItem());

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_NuevaProvinciaActionPerformed

    private void btn_NuevaLocalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NuevaLocalidadActionPerformed
        GUI_DetalleLocalidad gui_DetalleLocalidad = new GUI_DetalleLocalidad();
        gui_DetalleLocalidad.setModal(true);
        gui_DetalleLocalidad.setLocationRelativeTo(this);
        gui_DetalleLocalidad.setVisible(true);

        try {
            cargarComboBoxLocalidadesDeLaProvincia((Provincia) cmb_Provincia.getSelectedItem());

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_NuevaLocalidadActionPerformed

    private void btn_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_GuardarActionPerformed
        try {
            //TO DO - Esta validacion debería ser hecha por un componente swing            
            String cuip_ingresado = txt_CUIP.getText().trim();
            if (cuip_ingresado.equals("")) {
                cuip_ingresado = "0";
            }
            if (!Validator.esNumericoPositivo(cuip_ingresado)) {
                throw new ServiceException("El CUIT/CUIL/CUIP ingresado es inválido.");
            }

            String ingBrutos_ingresado = txt_IngBrutos.getText().trim();
            if (ingBrutos_ingresado.equals("")) {
                ingBrutos_ingresado = "0";
            }
            if (!Validator.esNumericoPositivo(ingBrutos_ingresado)) {
                throw new ServiceException("Ing. Brutos ingresado es inválido.");
            }

            String mensaje = "";
            if (tipoDeOperacion == TipoDeOperacion.ALTA) {
                Empresa empresa = new Empresa();
                empresa.setNombre(txt_Nombre.getText().trim());
                empresa.setLema(txt_Lema.getText().trim());
                empresa.setDireccion(txt_Direccion.getText().trim());
                empresa.setCondicionIVA((CondicionIVA) cmb_CondicionIVA.getSelectedItem());
                empresa.setCuip(Long.parseLong(cuip_ingresado));
                empresa.setIngresosBrutos(Long.parseLong(ingBrutos_ingresado));
                empresa.setFechaInicioActividad(dc_FechaInicioActividad.getDate());
                empresa.setEmail(txt_Email.getText().trim());
                empresa.setTelefono(txt_Telefono.getText().trim());
                empresa.setLocalidad((Localidad) cmb_Localidad.getSelectedItem());
                empresa.setLogo(logo);

                empresaService.guardar(empresa);
                mensaje = "La Empresa se guardó correctamente.";
            }

            if (tipoDeOperacion == TipoDeOperacion.ACTUALIZACION) {
                empresaSeleccionada.setNombre(txt_Nombre.getText().trim());
                empresaSeleccionada.setLema(txt_Lema.getText().trim());
                empresaSeleccionada.setDireccion(txt_Direccion.getText().trim());
                empresaSeleccionada.setCondicionIVA((CondicionIVA) cmb_CondicionIVA.getSelectedItem());
                empresaSeleccionada.setCuip(Long.parseLong(cuip_ingresado));
                empresaSeleccionada.setIngresosBrutos(Long.parseLong(ingBrutos_ingresado));
                empresaSeleccionada.setFechaInicioActividad(dc_FechaInicioActividad.getDate());
                empresaSeleccionada.setEmail(txt_Email.getText().trim());
                empresaSeleccionada.setTelefono(txt_Telefono.getText().trim());
                empresaSeleccionada.setLocalidad((Localidad) cmb_Localidad.getSelectedItem());
                empresaSeleccionada.setLogo(logo);

                empresaService.actualizar(empresaSeleccionada);
                mensaje = "La Empresa se modificó correctamente.";
            }

            JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
            tipoDeOperacion = null;
            plegarDetalle();
            limpiarYRecargarComponentes();
            cargarListEmpresas();

        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_GuardarActionPerformed

    private void btn_EliminarEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EliminarEmpresaActionPerformed
        if (empresaSeleccionada != null) {
            try {
                int respuesta = JOptionPane.showConfirmDialog(this,
                        "¿Esta seguro que desea eliminar la empresa: "
                        + empresaSeleccionada.getNombre() + "?",
                        "Eliminar", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    empresaService.eliminar(empresaSeleccionada);

                    //actualiza la empresa en caso de que sea la seleccionada
                    if (empresaSeleccionada.equals(empresaService.getEmpresaActiva().getEmpresa())) {
                        empresaService.setEmpresaActiva(null);
                    }
                    this.cargarListEmpresas();
                    empresaSeleccionada = null;
                }

            } catch (PersistenceException ex) {
                log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
                JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una Empresa para poder eliminarla.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_EliminarEmpresaActionPerformed

    private void btn_ExaminarArchivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ExaminarArchivosActionPerformed
        JFileChooser menuElegirLogo = new JFileChooser();
        menuElegirLogo.setAcceptAllFileFilterUsed(false);
        menuElegirLogo.addChoosableFileFilter(new FiltroImagenes());

        try {
            if (menuElegirLogo.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                if (Utilidades.esTamanioValido(menuElegirLogo.getSelectedFile(), 512000)) {
                    File file = menuElegirLogo.getSelectedFile();
                    logo = Utilidades.convertirFileIntoByteArray(file);
                    ImageIcon logoProvisional = new ImageIcon(menuElegirLogo.getSelectedFile().getAbsolutePath());
                    ImageIcon logoRedimensionado = new ImageIcon(logoProvisional.getImage().getScaledInstance(114, 114, Image.SCALE_SMOOTH));
                    lbl_Logo.setIcon(logoRedimensionado);
                    lbl_Logo.setText("");

                } else {
                    JOptionPane.showMessageDialog(this, "El tamaño del archivo seleccionado, supera el límite de 512kb.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    logo = null;
                }
            } else {
                logo = null;
            }

        } catch (IOException ex) {
            log.error(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_ErrorIOException") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_ErrorIOException"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_ExaminarArchivosActionPerformed

    private void btn_EliminarLogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EliminarLogoActionPerformed
        lbl_Logo.setIcon(null);
        lbl_Logo.setText("SIN IMAGEN");
        logo = null;
    }//GEN-LAST:event_btn_EliminarLogoActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            cargarListEmpresas();
            cargarComboBoxCondicionesIVA();
            cargarComboBoxPaises();

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            if (empresaService.getEmpresas().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No puede seguir operando con S.I.C."
                        + " si no posee Empresas.\nDebe crear una nueva para poder continuar.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                this.dispose();
            }

        } catch (PersistenceException ex) {
            log.error(ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos") + " - " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Mensajes").getString("mensaje_error_acceso_a_datos"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_formWindowClosing
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Cancelar;
    private javax.swing.JButton btn_EliminarEmpresa;
    private javax.swing.JButton btn_EliminarLogo;
    private javax.swing.JButton btn_ExaminarArchivos;
    private javax.swing.JButton btn_Guardar;
    private javax.swing.JButton btn_ModificarEmpresa;
    private javax.swing.JButton btn_NuevaCondicionIVA;
    private javax.swing.JButton btn_NuevaEmpresa;
    private javax.swing.JButton btn_NuevaLocalidad;
    private javax.swing.JButton btn_NuevaProvincia;
    private javax.swing.JButton btn_NuevoPais;
    private javax.swing.JComboBox cmb_CondicionIVA;
    private javax.swing.JComboBox cmb_Localidad;
    private javax.swing.JComboBox cmb_Pais;
    private javax.swing.JComboBox cmb_Provincia;
    private com.toedter.calendar.JDateChooser dc_FechaInicioActividad;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel lbl_CUIP;
    private javax.swing.JLabel lbl_CondicionIVA;
    private javax.swing.JLabel lbl_Direccion;
    private javax.swing.JLabel lbl_Email;
    private javax.swing.JLabel lbl_FIA;
    private javax.swing.JLabel lbl_IngBrutos;
    private javax.swing.JLabel lbl_Lema;
    private javax.swing.JLabel lbl_Localidad;
    private javax.swing.JLabel lbl_Logo;
    private javax.swing.JLabel lbl_MisEmpresas;
    private javax.swing.JLabel lbl_Nombre;
    private javax.swing.JLabel lbl_Pais;
    private javax.swing.JLabel lbl_Provincia;
    private javax.swing.JLabel lbl_Telefono;
    private javax.swing.JList lst_Empresas;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JScrollPane sp_Empresas;
    private javax.swing.JTextField txt_CUIP;
    private javax.swing.JTextField txt_Direccion;
    private javax.swing.JTextField txt_Email;
    private javax.swing.JTextField txt_IngBrutos;
    private javax.swing.JTextField txt_Lema;
    private javax.swing.JTextField txt_Nombre;
    private javax.swing.JTextField txt_Telefono;
    // End of variables declaration//GEN-END:variables
}
