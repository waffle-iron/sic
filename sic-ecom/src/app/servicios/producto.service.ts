import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class ProductoService {
    productoJson = {
        "productos": [
            {
                "codigo": "001",
                "descripcion": "Tornillo bolsita",
                "cantidad": "1700",
                "precio": "23"
            },
            {
                "codigo": "002",
                "descripcion": "Cemento bolsa",
                "cantidad": "500",
                "precio": "15"
            },
            {
                "codigo": "003",
                "descripcion": "pintura tacho 5L",
                "cantidad": "70",
                "precio": "634"
            },
            {
                "codigo": "005",
                "descripcion": "pinza amperometrica hasta 20 A.",
                "cantidad": "160",
                "precio": "2000"
            },
            {
                "codigo": "006",
                "descripcion": "taladro told",
                "cantidad": "10",
                "precio": "1023"
            },
            {
                "codigo": "009",
                "descripcion": "videt para el baño",
                "cantidad": "47",
                "precio": "3549"
            }
        ]
    }

    respuestaJ = [];

    private url1: string = "";
    private urlHttp : string = "http://echo.jsontest.com/key/value/one/two";

    constructor(
        private http: Http
    ) { }

    getProductos() {
        debugger
        this.http.get(this.urlHttp).do
        ((res) => console.log(res));
        this.http.get(this.urlHttp)
                        // ...and calling .json() on the response to return data
                         .map((res:Response) => console.log(res.json()));
        //console.log(this.respuestaJ);
        return this.productoJson;
    }
}