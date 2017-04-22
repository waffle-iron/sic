import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  moduleId: module.id,
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['app.component.scss']
})
export class AppComponent implements OnInit {

  stateCtrl: FormControl;
  filteredStates: any;
  
  constructor() {
    this.stateCtrl = new FormControl();
  }

  ngOnInit() {
  }
}
