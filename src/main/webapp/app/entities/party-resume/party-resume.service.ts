import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPartyResume } from 'app/shared/model/party-resume.model';

type EntityResponseType = HttpResponse<IPartyResume>;
type EntityArrayResponseType = HttpResponse<IPartyResume[]>;

@Injectable({ providedIn: 'root' })
export class PartyResumeService {
  public resourceUrl = SERVER_API_URL + 'api/party-resumes';

  constructor(protected http: HttpClient) {}

  create(partyResume: IPartyResume): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyResume);
    return this.http
      .post<IPartyResume>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(partyResume: IPartyResume): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partyResume);
    return this.http
      .put<IPartyResume>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPartyResume>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartyResume[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(partyResume: IPartyResume): IPartyResume {
    const copy: IPartyResume = Object.assign({}, partyResume, {
      resumeDate: partyResume.resumeDate && partyResume.resumeDate.isValid() ? partyResume.resumeDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.resumeDate = res.body.resumeDate ? moment(res.body.resumeDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((partyResume: IPartyResume) => {
        partyResume.resumeDate = partyResume.resumeDate ? moment(partyResume.resumeDate) : undefined;
      });
    }
    return res;
  }
}
