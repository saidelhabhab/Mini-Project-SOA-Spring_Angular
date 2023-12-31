import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { DemoAngularMateriel } from '../DemoAngularMateriel';
import { PostCategoryComponent } from './components/post-category/post-category.component';
import { PostProductComponent } from './components/post-product/post-product.component';
import { PostCouponComponent } from './components/post-coupon/post-coupon.component';
import { CouponsComponent } from './components/coupons/coupons.component';
import { OrdersComponent } from './components/orders/orders.component';
import { UpdateProductComponent } from './components/update-product/update-product.component';
import { UsersComponent } from './components/users/users.component';
import { UpdateUserComponent } from './components/update-user/update-user.component';


@NgModule({
  declarations: [
    AdminComponent,
    DashboardComponent,
    PostCategoryComponent,
    PostProductComponent,
    PostCouponComponent,
    CouponsComponent,
    OrdersComponent,
    UpdateProductComponent,
    UsersComponent,
    UpdateUserComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    DemoAngularMateriel
  ]
})
export class AdminModule { }
