import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class ProductService {
    private productsJson: any[] = [
        {
            "codigo": "001",
            "descripcion": "Tornillo bolsita",
            "cantidad": "1700",
            "precio": "23",
            "medida": 15,
            "imageUrl": "assets/placeholderProducto.png"
        },
        {
            "codigo": "002",
            "descripcion": "Cemento bolsa",
            "cantidad": "500",
            "precio": "15",
            "medida": 15,
            "imageUrl": "assets/placeholderProducto.png"
        },
        {
            "codigo": "003",
            "descripcion": "pintura tacho 5L",
            "cantidad": "70",
            "precio": "634",
            "medida": 15,
            "imageUrl": "assets/placeholderProducto.png"
        },
        {
            "codigo": "005",
            "descripcion": "pinza amperometrica hasta 20 A.",
            "cantidad": "160",
            "precio": "2000",
            "medida": 15,
            "imageUrl": "assets/placeholderProducto.png"
        },
        {
            "codigo": "006",
            "descripcion": "taladro told",
            "cantidad": "10",
            "precio": "1023",
            "medida": 15,
            "imageUrl": "assets/placeholderProducto.png"
        },
        {
            "codigo": "009",
            "descripcion": "videt para el baño",
            "cantidad": "47",
            "precio": "3549",
            "medida": 15,
            "imageUrl": "assets/placeholderProducto.png"
        }
    ]

    private respuestaJ: any[] = [];

    private url1: string = "";
    private urlHttp : string = "http://echo.jsontest.com/key/value/one/two";

    constructor(
        private http: Http
    ) { }

    getProducts() {
        return this.productsJson;
    }
}
