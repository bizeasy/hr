import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITaxSlab } from 'app/shared/model/tax-slab.model';

type EntityResponseType = HttpResponse<ITaxSlab>;
type EntityArrayResponseType = HttpResponse<ITaxSlab[]>;

@Injectable({ providedIn: 'root' })
export class TaxSlabService {
  public resourceUrl = SERVER_API_URL + 'api/tax-slabs';

  constructor(protected http: HttpClient) {}

  create(taxSlab: ITaxSlab): Observable<EntityResponseType> {
    return this.http.post<ITaxSlab>(this.resourceUrl, taxSlab, { observe: 'response' });
  }

  update(taxSlab: ITaxSlab): Observable<EntityResponseType> {
    return this.http.put<ITaxSlab>(this.resourceUrl, taxSlab, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITaxSlab>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITaxSlab[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
