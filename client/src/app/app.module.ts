import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { LoanCreateComponent } from './components/loan-create/loan-create.component';
import { LoanStatusComponent } from './components/loan-status/loan-status.component';
import { HomeComponent } from './components/home/home.component';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AutofocusDirective } from './autofocus.directive';


@NgModule({
  declarations: [
    AppComponent,
    LoanCreateComponent,
    LoanStatusComponent,
    HomeComponent,
    AutofocusDirective
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
