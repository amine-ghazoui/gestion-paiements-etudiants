import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AdminTemplateComponent } from './admin-template/admin-template.component';
import {MatToolbar, MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatMenu, MatMenuModule} from '@angular/material/menu';
import {MatSidenav, MatSidenavModule} from '@angular/material/sidenav';
import {MatList, MatListModule} from '@angular/material/list';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { LoadStudentsComponent } from './load-students/load-students.component';
import { LoadPaymentsComponent } from './load-payments/load-payments.component';
import { LoginComponent } from './login/login.component';
import { StudentsComponent } from './students/students.component';
import { PaymentsComponent } from './payments/payments.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import {MatCard, MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInput, MatInputModule} from '@angular/material/input';
import {ReactiveFormsModule} from '@angular/forms';
import {provideAnimations} from '@angular/platform-browser/animations';
import {AuthGuard} from './guards/auth.guard';
import {AuthorizationGuard} from './guards/authorization.guard';
import {HttpClientModule} from '@angular/common/http';
import {MatTable, MatTableModule} from '@angular/material/table';
@NgModule({
  declarations: [
    AppComponent,
    AdminTemplateComponent,
    HomeComponent,
    ProfileComponent,
    LoadStudentsComponent,
    LoadPaymentsComponent,
    LoginComponent,
    StudentsComponent,
    PaymentsComponent,
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatSidenavModule,
    MatListModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    //pour récupérer les données du formulaire
    ReactiveFormsModule,
    HttpClientModule,
    MatTableModule
  ],
  providers: [
    provideAnimations(), AuthGuard, AuthorizationGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
