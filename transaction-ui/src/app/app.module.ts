import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AgGridModule} from "ag-grid-angular";
import {HomeComponent} from "./home/home.component";
import {DailyReportSummaryComponent} from "./daily-report-summary/daily-report-summary.component";
import {BsDatepickerModule} from 'ngx-bootstrap/datepicker';
import {StoreModule} from "@ngrx/store";
import {TransactionReducer} from "./daily-report-summary/shared/transaction.reducer";
import {EffectsModule} from "@ngrx/effects";
import {TransactionService} from "./daily-report-summary/shared/transaction.service";
import {HttpClientModule} from "@angular/common/http";
import {DatePipe} from "@angular/common";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    DailyReportSummaryComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    AgGridModule.withComponents([]),
    EffectsModule.forRoot([TransactionService]),
    BsDatepickerModule.forRoot(),
    StoreModule.forRoot({ transactions: TransactionReducer })
  ],
  providers: [DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule {
}
