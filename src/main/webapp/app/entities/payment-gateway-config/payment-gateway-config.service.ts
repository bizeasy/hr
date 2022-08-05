import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPaymentGatewayConfig } from 'app/shared/model/payment-gateway-config.model';

type EntityResponseType = HttpResponse<IPaymentGatewayConfig>;
type EntityArrayResponseType = HttpResponse<IPaymentGatewayConfig[]>;

@Injectable({ providedIn: 'root' })
export class PaymentGatewayConfigService {
  public resourceUrl = SERVER_API_URL + 'api/payment-gateway-configs';

  constructor(protected http: HttpClient) {}

  create(paymentGatewayConfig: IPaymentGatewayConfig): Observable<EntityResponseType> {
    return this.http.post<IPaymentGatewayConfig>(this.resourceUrl, paymentGatewayConfig, { observe: 'response' });
  }

  update(paymentGatewayConfig: IPaymentGatewayConfig): Observable<EntityResponseType> {
    return this.http.put<IPaymentGatewayConfig>(this.resourceUrl, paymentGatewayConfig, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaymentGatewayConfig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaymentGatewayConfig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
