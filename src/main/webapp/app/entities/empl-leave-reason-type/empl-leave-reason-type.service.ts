import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmplLeaveReasonType } from 'app/shared/model/empl-leave-reason-type.model';

type EntityResponseType = HttpResponse<IEmplLeaveReasonType>;
type EntityArrayResponseType = HttpResponse<IEmplLeaveReasonType[]>;

@Injectable({ providedIn: 'root' })
export class EmplLeaveReasonTypeService {
  public resourceUrl = SERVER_API_URL + 'api/empl-leave-reason-types';

  constructor(protected http: HttpClient) {}

  create(emplLeaveReasonType: IEmplLeaveReasonType): Observable<EntityResponseType> {
    return this.http.post<IEmplLeaveReasonType>(this.resourceUrl, emplLeaveReasonType, { observe: 'response' });
  }

  update(emplLeaveReasonType: IEmplLeaveReasonType): Observable<EntityResponseType> {
    return this.http.put<IEmplLeaveReasonType>(this.resourceUrl, emplLeaveReasonType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmplLeaveReasonType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmplLeaveReasonType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
