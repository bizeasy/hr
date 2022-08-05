import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContactMechType } from 'app/shared/model/contact-mech-type.model';

type EntityResponseType = HttpResponse<IContactMechType>;
type EntityArrayResponseType = HttpResponse<IContactMechType[]>;

@Injectable({ providedIn: 'root' })
export class ContactMechTypeService {
  public resourceUrl = SERVER_API_URL + 'api/contact-mech-types';

  constructor(protected http: HttpClient) {}

  create(contactMechType: IContactMechType): Observable<EntityResponseType> {
    return this.http.post<IContactMechType>(this.resourceUrl, contactMechType, { observe: 'response' });
  }

  update(contactMechType: IContactMechType): Observable<EntityResponseType> {
    return this.http.put<IContactMechType>(this.resourceUrl, contactMechType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContactMechType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContactMechType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
