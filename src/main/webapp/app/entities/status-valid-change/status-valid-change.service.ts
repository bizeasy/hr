import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStatusValidChange } from 'app/shared/model/status-valid-change.model';

type EntityResponseType = HttpResponse<IStatusValidChange>;
type EntityArrayResponseType = HttpResponse<IStatusValidChange[]>;

@Injectable({ providedIn: 'root' })
export class StatusValidChangeService {
  public resourceUrl = SERVER_API_URL + 'api/status-valid-changes';

  constructor(protected http: HttpClient) {}

  create(statusValidChange: IStatusValidChange): Observable<EntityResponseType> {
    return this.http.post<IStatusValidChange>(this.resourceUrl, statusValidChange, { observe: 'response' });
  }

  update(statusValidChange: IStatusValidChange): Observable<EntityResponseType> {
    return this.http.put<IStatusValidChange>(this.resourceUrl, statusValidChange, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStatusValidChange>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStatusValidChange[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
