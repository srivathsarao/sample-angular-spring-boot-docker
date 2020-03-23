import {Transaction} from "./transaction.model";

export default class TransactionState {
  transactions: Array<Transaction>;
  error: Error
}

export const initializeState = (): TransactionState => {
  return {transactions: [], error: null};
};
