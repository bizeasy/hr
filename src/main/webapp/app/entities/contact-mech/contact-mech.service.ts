import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContactMech } from 'app/shared/model/contact-mech.model';

type EntityResponseType = HttpResponse<IContactMech>;
type EntityArrayResponseType = HttpResponse<IContactMech[]>;

@Injectable({ providedIn: 'root' })
export class ContactMechService {
  public resourceUrl = SERVER_API_URL + 'api/contact-meches';

  constructor(protected http: HttpClient) {}

  create(contactMech: IContactMech): Observable<EntityResponseType> {
    return this.http.post<IContactMech>(this.resourceUrl, contactMech, { observe: 'response' });
  }

  update(contactMech: IContactMech): Observable<EntityResponseType> {
    return this.http.put<IContactMech>(this.resourceUrl, contactMech, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContactMech>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContactMech[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
