import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWorkEffortStatus } from 'app/shared/model/work-effort-status.model';

type EntityResponseType = HttpResponse<IWorkEffortStatus>;
type EntityArrayResponseType = HttpResponse<IWorkEffortStatus[]>;

@Injectable({ providedIn: 'root' })
export class WorkEffortStatusService {
  public resourceUrl = SERVER_API_URL + 'api/work-effort-statuses';

  constructor(protected http: HttpClient) {}

  create(workEffortStatus: IWorkEffortStatus): Observable<EntityResponseType> {
    return this.http.post<IWorkEffortStatus>(this.resourceUrl, workEffortStatus, { observe: 'response' });
  }

  update(workEffortStatus: IWorkEffortStatus): Observable<EntityResponseType> {
    return this.http.put<IWorkEffortStatus>(this.resourceUrl, workEffortStatus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWorkEffortStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWorkEffortStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
