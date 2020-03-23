import {ClientInformation} from "./client-information.model";
import {ProductInformation} from "./product-information.model";

export interface Transaction {
  clientInformation: ClientInformation;
  productInformation: ProductInformation;
  totalTransactionAmount: number;
}
