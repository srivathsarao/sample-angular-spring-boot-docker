import {Component, OnInit} from "@angular/core";
import {select, Store} from "@ngrx/store";
import TransactionState from "./shared/transaction.state";
import {Observable} from "rxjs";
import * as TransactionActions from './shared/transaction.action';
import {Transaction} from "./shared/transaction.model";
import {DatePipe} from "@angular/common";
import {ColDef, ColGroupDef, GridOptions} from "ag-grid-community";
import "ag-grid-enterprise";

@Component({
  selector: 'app-home',
  templateUrl: './daily-report-summary.component.html'
})
export class DailyReportSummaryComponent implements OnInit {

  private selectedDate = new Date(2010, 7, 19);
  private columnDefs: ColGroupDef[] = [
    {
      headerName: 'Product Information',
      children: [
        {headerName: 'symbol', field: 'productInformation.symbol', rowGroup: true},
        {headerName: 'productGroupCode', field: 'productInformation.productGroupCode'},
        {headerName: 'exchangeCode', field: 'productInformation.exchangeCode'},
        {headerName: 'expirationDate', field: 'productInformation.expirationDate'}
      ]
    },
    {
      headerName: 'Total Transaction Amount',
      children: [
        {headerName: 'totalTransactionAmount', field: 'totalTransactionAmount', aggFunc: 'sum'}
      ]
    },
    {
      headerName: 'Client Information',
      children: [
        {headerName: 'clientNumber', field: 'clientInformation.clientNumber'},
        {headerName: 'accountNumber', field: 'clientInformation.accountNumber'},
        {headerName: 'clientType', field: 'clientInformation.clientType'},
        {headerName: 'subAccountNumber', field: 'clientInformation.subAccountNumber'}
      ]
    }
  ];

  private transactionsObservable: Observable<TransactionState>;
  private transactions: Array<Transaction> = [];
  private gridOptions: GridOptions = {columnDefs: this.columnDefs, groupDefaultExpanded: 1};

  constructor(private store: Store<{ transactions: TransactionState }>, private datePipe: DatePipe) {
    this.transactionsObservable = store.pipe(select('transactions'));
  }

  ngOnInit(): void {
    this.transactionsObservable.subscribe(state => {
      this.transactions = state.transactions
    });
    this.dispatch();
  }

  onDateChanged(selectedDate: Date) {
    this.selectedDate = selectedDate;
    this.dispatch();
  }

  dispatch() {

    this.store.dispatch(TransactionActions.FILTER_TRANSACTION_ACTION(
      {payload: this.datePipe.transform(this.selectedDate, 'ddMMyyyy')}));
  }

  downloadCsv() {
    this.gridOptions.api.exportDataAsCsv();
  }
}
