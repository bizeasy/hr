import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPostalAddress } from 'app/shared/model/postal-address.model';

type EntityResponseType = HttpResponse<IPostalAddress>;
type EntityArrayResponseType = HttpResponse<IPostalAddress[]>;

@Injectable({ providedIn: 'root' })
export class PostalAddressService {
  public resourceUrl = SERVER_API_URL + 'api/postal-addresses';

  constructor(protected http: HttpClient) {}

  create(postalAddress: IPostalAddress): Observable<EntityResponseType> {
    return this.http.post<IPostalAddress>(this.resourceUrl, postalAddress, { observe: 'response' });
  }

  update(postalAddress: IPostalAddress): Observable<EntityResponseType> {
    return this.http.put<IPostalAddress>(this.resourceUrl, postalAddress, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPostalAddress>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPostalAddress[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
