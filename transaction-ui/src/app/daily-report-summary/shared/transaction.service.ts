import {Injectable} from "@angular/core";
import {Actions, createEffect, Effect, ofType} from "@ngrx/effects";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {Transaction} from "./transaction.model";
import * as TransactionAction from "./transaction.action";
import {catchError, map, mergeMap} from "rxjs/operators";
import {Action} from "@ngrx/store";

@Injectable()
export class TransactionService {

  constructor(private http: HttpClient, private action: Actions) {
  }

  @Effect()
  getTransaction: Observable<Action> = createEffect(() =>
    this.action.pipe(
      ofType(TransactionAction.FILTER_TRANSACTION_ACTION),
      mergeMap(action => {
          let params = new HttpParams().set('date', action.payload);
          return this.http.get('/api/transaction', {params: params}).pipe(
            map((data: Transaction[]) => {
              return TransactionAction.FILTER_TRANSACTION_SUCCESS_ACTION({payload: data});
            }),
            catchError((error: Error) => {
              return of(TransactionAction.FILTER_TRANSACTION_ERROR_ACTION(error));
            })
          )
        }
      )
    )
  );
}
