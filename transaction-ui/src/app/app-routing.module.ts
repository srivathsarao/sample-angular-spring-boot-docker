import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {DailyReportSummaryComponent} from "./daily-report-summary/daily-report-summary.component";
import {HomeComponent} from "./home/home.component";


const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'summary', component: DailyReportSummaryComponent }
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
