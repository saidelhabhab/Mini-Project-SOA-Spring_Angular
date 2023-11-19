import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserStorageService } from 'src/app/storage/user-storage-service';


const Basic_URL="http://localhost:8000/"

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  
  constructor(private http: HttpClient) { }

 /////////////////////////////////////////

  getAllProducts(): Observable<any>{
    return this.http.get(Basic_URL+ "api/client/products",{
      headers:this.createAuthorizationHeader()
    });
  }

  getAllProductsByName(name:any): Observable<any>{
    return this.http.get(Basic_URL+ `api/client/search/${name}`,{
      headers:this.createAuthorizationHeader()
    });
  }

 /////////////////////////////////////////

   addToCart(productId:any): Observable<any>{
    
    const cartDto = {  
      productId : productId,   
      clientId: UserStorageService.getUserId(),
    }

    //console.log("cartDto" + JSON.stringify(cartDto))


    return this.http.post(Basic_URL+ `api/client/cart`,cartDto , {
      headers:this.createAuthorizationHeader()
    });
  }


  getCartByClientId(): Observable<any>{
    
    const clientId = UserStorageService.getUserId();
   // console.log("client_id: " + clientId)

    return this.http.get(Basic_URL+ `api/client/cart/${clientId}`, {
      headers:this.createAuthorizationHeader()
    });
  }

  
  /////////////////////////////////////////
  getFactureByClientId(): Observable<any>{
    
    const clientId = UserStorageService.getUserId();
    //console.log("client_id: " + clientId)

    return this.http.get(Basic_URL+ `api/client/MyOrders/${clientId}`, {
      headers:this.createAuthorizationHeader()
    });
  }

  /////////////////////////////////////////

  applyCoupon(code:any): Observable<any>{
    
    const clientId = UserStorageService.getUserId();

    return this.http.get(Basic_URL+ `api/client/coupon/${clientId}/${code}`, {
      headers:this.createAuthorizationHeader()
    });
  }


  increaseProductQuantity(productId:any): Observable<any>{
    
    const cartDto = {  
      productId : productId,   
      clientId: UserStorageService.getUserId(),
    }
    //console.log("productId: " + productId)
     return this.http.post(Basic_URL+ `api/client/addition`,cartDto , {
      headers:this.createAuthorizationHeader()
    });
  }


  decreaseProductQuantity(productId:any): Observable<any>{
    
    const cartDto = {  
      productId : productId,   
      clientId: UserStorageService.getUserId(),
    }

     return this.http.post(Basic_URL+ `api/client/deduction`,cartDto , {
      headers:this.createAuthorizationHeader()
    });
  }

  delete(productId:any): Observable<any>{
    
    const cartDto = {  
      productId : productId,   
      clientId: UserStorageService.getUserId(),
    }
      console.log(" //"+ productId);
     return this.http.put(Basic_URL+ `api/client/cart/delete`,cartDto , {
      headers:this.createAuthorizationHeader()
    });
  }


  
  /////////////////////////////////////////

  playerOrder(orderDto:any): Observable<any>{
    
     orderDto.clientId = UserStorageService.getUserId();

    return this.http.post(Basic_URL+ `api/client/placeOrder`, orderDto, {
      headers:this.createAuthorizationHeader()
    });
  }


 /////////////////////////////////////////

  updateProfile(profileDTO:any): Observable<any>{
    const profileId = {  
      profileId: UserStorageService.getUserId(),
}
    return this.http.put(Basic_URL + `api/client/profile/${profileId.profileId}`, profileDTO,{
      headers:this.createAuthorizationHeader()
    });
  }
  
  
   getProfileById(): Observable<any>{

    const profileId = {  
      profileId: UserStorageService.getUserId(),
    }
    console.log("profileId" +profileId)
      return this.http.get(Basic_URL+ `api/client/getProfileById/${profileId.profileId}`,{
        headers:this.createAuthorizationHeader()
      });
    }

  /////////////////////////////////////////

  private createAuthorizationHeader():HttpHeaders{
    return new HttpHeaders().set(
      'Authorization','Bearer ' + UserStorageService.getToken()
    )
  }

}
