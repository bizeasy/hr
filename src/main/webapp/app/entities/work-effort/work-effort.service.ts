import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWorkEffort } from 'app/shared/model/work-effort.model';

type EntityResponseType = HttpResponse<IWorkEffort>;
type EntityArrayResponseType = HttpResponse<IWorkEffort[]>;

@Injectable({ providedIn: 'root' })
export class WorkEffortService {
  public resourceUrl = SERVER_API_URL + 'api/work-efforts';

  constructor(protected http: HttpClient) {}

  create(workEffort: IWorkEffort): Observable<EntityResponseType> {
    return this.http.post<IWorkEffort>(this.resourceUrl, workEffort, { observe: 'response' });
  }

  update(workEffort: IWorkEffort): Observable<EntityResponseType> {
    return this.http.put<IWorkEffort>(this.resourceUrl, workEffort, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWorkEffort>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWorkEffort[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
