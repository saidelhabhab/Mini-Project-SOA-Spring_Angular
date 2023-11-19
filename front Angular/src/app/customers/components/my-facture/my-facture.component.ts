import { Component, OnInit } from '@angular/core';
import { CustomerService } from '../../services/customer.service';

@Component({
  selector: 'app-my-facture',
  templateUrl: './my-facture.component.html',
  styleUrls: ['./my-facture.component.scss']
})
export class MyFactureComponent  implements OnInit {

  order :any;
  constructor(private customerService:CustomerService) {}


  ngOnInit(): void {
    this.getOrders();
  }

  getOrders(){
    this.customerService.getFactureByClientId().subscribe(res=>{
      //console.log("my orders", res);
      this.order = res;
    })
  }





}
