import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInvoiceType } from 'app/shared/model/invoice-type.model';

type EntityResponseType = HttpResponse<IInvoiceType>;
type EntityArrayResponseType = HttpResponse<IInvoiceType[]>;

@Injectable({ providedIn: 'root' })
export class InvoiceTypeService {
  public resourceUrl = SERVER_API_URL + 'api/invoice-types';

  constructor(protected http: HttpClient) {}

  create(invoiceType: IInvoiceType): Observable<EntityResponseType> {
    return this.http.post<IInvoiceType>(this.resourceUrl, invoiceType, { observe: 'response' });
  }

  update(invoiceType: IInvoiceType): Observable<EntityResponseType> {
    return this.http.put<IInvoiceType>(this.resourceUrl, invoiceType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInvoiceType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInvoiceType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
