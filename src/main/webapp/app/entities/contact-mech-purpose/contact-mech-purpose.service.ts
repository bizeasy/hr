import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContactMechPurpose } from 'app/shared/model/contact-mech-purpose.model';

type EntityResponseType = HttpResponse<IContactMechPurpose>;
type EntityArrayResponseType = HttpResponse<IContactMechPurpose[]>;

@Injectable({ providedIn: 'root' })
export class ContactMechPurposeService {
  public resourceUrl = SERVER_API_URL + 'api/contact-mech-purposes';

  constructor(protected http: HttpClient) {}

  create(contactMechPurpose: IContactMechPurpose): Observable<EntityResponseType> {
    return this.http.post<IContactMechPurpose>(this.resourceUrl, contactMechPurpose, { observe: 'response' });
  }

  update(contactMechPurpose: IContactMechPurpose): Observable<EntityResponseType> {
    return this.http.put<IContactMechPurpose>(this.resourceUrl, contactMechPurpose, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContactMechPurpose>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContactMechPurpose[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
