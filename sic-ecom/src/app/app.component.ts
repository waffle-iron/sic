import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

import { ProductoService } from './servicios/producto.service';

@Component({
  selector: 'app-root',
  providers: [ProductoService],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  ngOnInit() {

  }

  stateCtrl: FormControl;
  filteredStates: any;

  productos: Array<any>;

  constructor(private productoService: ProductoService) {
    this.stateCtrl = new FormControl();
  }

  cargarProductos() {
    this.productos = this.productoService.getProductos().productos;
  }
}
