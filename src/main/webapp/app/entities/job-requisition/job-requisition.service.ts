import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IJobRequisition } from 'app/shared/model/job-requisition.model';

type EntityResponseType = HttpResponse<IJobRequisition>;
type EntityArrayResponseType = HttpResponse<IJobRequisition[]>;

@Injectable({ providedIn: 'root' })
export class JobRequisitionService {
  public resourceUrl = SERVER_API_URL + 'api/job-requisitions';

  constructor(protected http: HttpClient) {}

  create(jobRequisition: IJobRequisition): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobRequisition);
    return this.http
      .post<IJobRequisition>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(jobRequisition: IJobRequisition): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobRequisition);
    return this.http
      .put<IJobRequisition>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IJobRequisition>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IJobRequisition[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(jobRequisition: IJobRequisition): IJobRequisition {
    const copy: IJobRequisition = Object.assign({}, jobRequisition, {
      requiredOnDate:
        jobRequisition.requiredOnDate && jobRequisition.requiredOnDate.isValid()
          ? jobRequisition.requiredOnDate.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.requiredOnDate = res.body.requiredOnDate ? moment(res.body.requiredOnDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((jobRequisition: IJobRequisition) => {
        jobRequisition.requiredOnDate = jobRequisition.requiredOnDate ? moment(jobRequisition.requiredOnDate) : undefined;
      });
    }
    return res;
  }
}
