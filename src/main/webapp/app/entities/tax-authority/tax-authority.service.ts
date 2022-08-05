import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITaxAuthority } from 'app/shared/model/tax-authority.model';

type EntityResponseType = HttpResponse<ITaxAuthority>;
type EntityArrayResponseType = HttpResponse<ITaxAuthority[]>;

@Injectable({ providedIn: 'root' })
export class TaxAuthorityService {
  public resourceUrl = SERVER_API_URL + 'api/tax-authorities';

  constructor(protected http: HttpClient) {}

  create(taxAuthority: ITaxAuthority): Observable<EntityResponseType> {
    return this.http.post<ITaxAuthority>(this.resourceUrl, taxAuthority, { observe: 'response' });
  }

  update(taxAuthority: ITaxAuthority): Observable<EntityResponseType> {
    return this.http.put<ITaxAuthority>(this.resourceUrl, taxAuthority, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITaxAuthority>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITaxAuthority[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
