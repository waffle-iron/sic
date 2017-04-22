import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/do';

@Injectable()
export class ProductService {

  constructor(private http: Http) {
  }

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

  getUsers(): Observable<any> {
    return this.http
      .get('https://jsonplaceholder.typicode.com/users')
      .map((response: Response) => response.json())
      .do(data => console.log(data))
      .catch(this.handleError);
  }

  handleError(error: Response) {
    console.error(error);
    const message = `Error status code ${error.status} at ${error.url}`;
    return Observable.throw(message);
  }

  getProducts() {
    return this.productsJson;
  }
}
