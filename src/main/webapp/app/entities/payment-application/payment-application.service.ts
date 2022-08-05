import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPaymentApplication } from 'app/shared/model/payment-application.model';

type EntityResponseType = HttpResponse<IPaymentApplication>;
type EntityArrayResponseType = HttpResponse<IPaymentApplication[]>;

@Injectable({ providedIn: 'root' })
export class PaymentApplicationService {
  public resourceUrl = SERVER_API_URL + 'api/payment-applications';

  constructor(protected http: HttpClient) {}

  create(paymentApplication: IPaymentApplication): Observable<EntityResponseType> {
    return this.http.post<IPaymentApplication>(this.resourceUrl, paymentApplication, { observe: 'response' });
  }

  update(paymentApplication: IPaymentApplication): Observable<EntityResponseType> {
    return this.http.put<IPaymentApplication>(this.resourceUrl, paymentApplication, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaymentApplication>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaymentApplication[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
