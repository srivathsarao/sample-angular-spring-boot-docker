import {Action, createAction, props} from '@ngrx/store';
import {Transaction} from "./transaction.model";

export const FILTER_TRANSACTION_ACTION = createAction(
  '[Transaction] - Filter Transaction',
  props<{ payload: string }>());

export const FILTER_TRANSACTION_SUCCESS_ACTION = createAction(
  '[Transaction] - Filter Transaction successful',
  props<{ payload: Array<Transaction> }>());

export const FILTER_TRANSACTION_ERROR_ACTION = createAction(
  '[ToDo] - Error',
  props<Error>());
