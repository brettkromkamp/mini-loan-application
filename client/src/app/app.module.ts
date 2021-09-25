import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoanCreateComponent } from './components/loan-create/loan-create.component';
import { LoanStatusComponent } from './components/loan-status/loan-status.component';

@NgModule({
  declarations: [
    AppComponent,
    LoanCreateComponent,
    LoanStatusComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
