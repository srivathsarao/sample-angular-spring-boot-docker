import {Action, createReducer, on} from "@ngrx/store";
import TransactionState, {initializeState} from "./transaction.state";
import * as TransactionActions from './transaction.action';

export const initialState = initializeState();

const reducer = createReducer(
  initialState,
  on(TransactionActions.FILTER_TRANSACTION_ACTION, state => state),
  on(TransactionActions.FILTER_TRANSACTION_SUCCESS_ACTION, (state: TransactionState, {payload}) => {
    return {...state, transactions: payload};
  }),
  on(TransactionActions.FILTER_TRANSACTION_ERROR_ACTION, (state: TransactionState, error: Error) => {
    console.log(error);
    return {...state, error: error};
  })
)

export function TransactionReducer(state: TransactionState | undefined, action: Action) {
  return reducer(state, action);
}
