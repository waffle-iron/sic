import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { AlertService } from '../servicios/alert.service';
import { AuthenticationService } from '../servicios/authentication.service';

@Component({
    moduleId: module.id,
    templateUrl: 'login.component.html'
})

export class LoginComponent implements OnInit {
    model: any = {};
    loading = false;
    returnUrl: string;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private authenticationService: AuthenticationService,
        private alertService: AlertService) { }

    ngOnInit() {
        // reset login status
        debugger
        this.authenticationService.logout();

        // get return url from route parameters or default to '/'
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    }

    login() {
      debugger
        this.loading = true;
        this.authenticationService.login(this.model.username, this.model.password);
        this.router.navigate([this.returnUrl]);
            /*.subscribe(
                data => {
                    debugger
                    this.router.navigate([this.returnUrl]);
                },
                error => {
                    debugger
                    this.alertService.error(error);
                    this.loading = false;
                });*/
    }
}