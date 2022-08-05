import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPaymentMethodType } from 'app/shared/model/payment-method-type.model';

type EntityResponseType = HttpResponse<IPaymentMethodType>;
type EntityArrayResponseType = HttpResponse<IPaymentMethodType[]>;

@Injectable({ providedIn: 'root' })
export class PaymentMethodTypeService {
  public resourceUrl = SERVER_API_URL + 'api/payment-method-types';

  constructor(protected http: HttpClient) {}

  create(paymentMethodType: IPaymentMethodType): Observable<EntityResponseType> {
    return this.http.post<IPaymentMethodType>(this.resourceUrl, paymentMethodType, { observe: 'response' });
  }

  update(paymentMethodType: IPaymentMethodType): Observable<EntityResponseType> {
    return this.http.put<IPaymentMethodType>(this.resourceUrl, paymentMethodType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaymentMethodType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaymentMethodType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
