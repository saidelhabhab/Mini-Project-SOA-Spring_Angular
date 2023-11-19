import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


import { DashboardComponent } from './components/dashboard/dashboard.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { DemoAngularMateriel } from '../DemoAngularMateriel';
import { NgxCaptchaModule } from 'ngx-captcha';
import { CostumersRoutingModule } from './customers-routing.module';
import { CartComponent } from './components/cart/cart.component';
import { PlaceOrderComponent } from './components/place-order/place-order.component';
import { CustomersComponent } from './customers.component';
import { MyFactureComponent } from './components/my-facture/my-facture.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';


@NgModule({
  declarations: [
    CustomersComponent,
    DashboardComponent,
    CartComponent,
    PlaceOrderComponent,
    MyFactureComponent,
    UserProfileComponent,
   
  ],
  imports: [
    CommonModule,
    CostumersRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    DemoAngularMateriel,
    NgxCaptchaModule
  ]
})
export class CustomersModule { }
