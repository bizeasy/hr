import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInvoiceItemType } from 'app/shared/model/invoice-item-type.model';

type EntityResponseType = HttpResponse<IInvoiceItemType>;
type EntityArrayResponseType = HttpResponse<IInvoiceItemType[]>;

@Injectable({ providedIn: 'root' })
export class InvoiceItemTypeService {
  public resourceUrl = SERVER_API_URL + 'api/invoice-item-types';

  constructor(protected http: HttpClient) {}

  create(invoiceItemType: IInvoiceItemType): Observable<EntityResponseType> {
    return this.http.post<IInvoiceItemType>(this.resourceUrl, invoiceItemType, { observe: 'response' });
  }

  update(invoiceItemType: IInvoiceItemType): Observable<EntityResponseType> {
    return this.http.put<IInvoiceItemType>(this.resourceUrl, invoiceItemType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInvoiceItemType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInvoiceItemType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
