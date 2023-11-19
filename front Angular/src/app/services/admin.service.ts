import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserStorageService } from '../storage/user-storage-service';
import { catchError } from 'rxjs/operators';
const Basic_URL="http://localhost:8000/"

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }


  addCategory(categoryDto:any): Observable<any>{
    return this.http.post(Basic_URL + "api/admin/category", categoryDto,{
      headers:this.createAuthorizationHeader()
    });
  }

  getAllCategories(): Observable<any>{
    return this.http.get(Basic_URL+ "api/admin/categories",{
      headers:this.createAuthorizationHeader()
    });
  }

  /////////////////////////////////////////

  addProduct(productDTO:any): Observable<any>{
    return this.http.post(Basic_URL + "api/admin/product", productDTO,{
      headers:this.createAuthorizationHeader()
    });
  }

  updateProduct(productDTO:any,productId:any): Observable<any>{
    return this.http.put(Basic_URL + `api/admin/product/${productId}`, productDTO,{
      headers:this.createAuthorizationHeader()
    });
  }

  getAllProducts(): Observable<any>{
    return this.http.get(Basic_URL+ "api/admin/products",{
      headers:this.createAuthorizationHeader()
    });
  }

   getProductsById(productId:any): Observable<any>{
    return this.http.get(Basic_URL+ `api/admin/getProductByID/${productId}`,{
      headers:this.createAuthorizationHeader()
    });
  }

  getAllProductsByName(name:any): Observable<any>{
    return this.http.get(Basic_URL+ `api/admin/search/${name}`,{
      headers:this.createAuthorizationHeader()
    });
  }

  deleteProduct(productId:any): Observable<any>{
    return this.http.delete(Basic_URL + `api/admin/product/${productId}`,{
      headers:this.createAuthorizationHeader()
    });
  }


   /////////////////////////////////////////

   addCoupon(couponDTO:any): Observable<any>{
    return this.http.post(Basic_URL + "api/admin/coupons", couponDTO,{
      headers:this.createAuthorizationHeader()
    });
  }

  getAllCoupon(): Observable<any>{
    return this.http.get(Basic_URL + "api/admin/coupons",{
      headers:this.createAuthorizationHeader()
    });
  }

  /////////////////////////////////////////

  getAllOrders(): Observable<any>{
    return this.http.get(Basic_URL + "api/admin/placeOrders",{
      headers:this.createAuthorizationHeader()
    });
  }

  changeOrderStatus(orderId:number, status:string): Observable<any>{
    return this.http.get(Basic_URL + `api/admin/order/${orderId}/${status}`,{
      headers:this.createAuthorizationHeader()
    });
  }

/////////////////////////////////////////

getAllUsers(): Observable<any>{
  return this.http.get(Basic_URL + "api/admin/clients",{
    headers:this.createAuthorizationHeader()
  });
}

updateClient(clientDTO:any,clientId:any): Observable<any>{
  return this.http.put(Basic_URL + `api/admin/client/${clientId}`, clientDTO,{
    headers:this.createAuthorizationHeader()
  });
}

deleteClient(clientId:any): Observable<any>{
  return this.http.delete(Basic_URL + `api/admin/client/${clientId}`,{
    headers:this.createAuthorizationHeader()
  });
}

 getClientById(clientId:any): Observable<any>{
    return this.http.get(Basic_URL+ `api/admin/getClientByID/${clientId}`,{
      headers:this.createAuthorizationHeader()
    });
  }


  /////////////////////////////////////////

  private createAuthorizationHeader():HttpHeaders{
    return new HttpHeaders().set(
      'Authorization','Bearer ' +UserStorageService.getToken()
    )
  }



 
}
