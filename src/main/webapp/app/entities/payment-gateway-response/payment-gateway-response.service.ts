import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPaymentGatewayResponse } from 'app/shared/model/payment-gateway-response.model';

type EntityResponseType = HttpResponse<IPaymentGatewayResponse>;
type EntityArrayResponseType = HttpResponse<IPaymentGatewayResponse[]>;

@Injectable({ providedIn: 'root' })
export class PaymentGatewayResponseService {
  public resourceUrl = SERVER_API_URL + 'api/payment-gateway-responses';

  constructor(protected http: HttpClient) {}

  create(paymentGatewayResponse: IPaymentGatewayResponse): Observable<EntityResponseType> {
    return this.http.post<IPaymentGatewayResponse>(this.resourceUrl, paymentGatewayResponse, { observe: 'response' });
  }

  update(paymentGatewayResponse: IPaymentGatewayResponse): Observable<EntityResponseType> {
    return this.http.put<IPaymentGatewayResponse>(this.resourceUrl, paymentGatewayResponse, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaymentGatewayResponse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaymentGatewayResponse[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
