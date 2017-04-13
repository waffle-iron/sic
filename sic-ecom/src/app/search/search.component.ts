import { Component, Output, EventEmitter } from '@angular/core';
import {FormControl} from '@angular/forms';
import 'rxjs/add/operator/startWith';

import { ProductoService } from '../servicios/producto.service';

@Component({
  selector: 'app-search',
  providers: [ProductoService],
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent {

  @Output() buscarClick = new EventEmitter<any>();

  stateCtrl: FormControl;
  filteredStates: any;

  productos: Array<any>;

  constructor(private productoService: ProductoService) {
    this.stateCtrl = new FormControl();
  }

  public onclick() {
    this.buscarClick.emit(this.productos);
  }

  cargarProductos() {
    debugger
    console.log(1);
    //this.productos = this.productoService.getProductos().productos;//.subscribe(data => this.states = data);
  }
}
